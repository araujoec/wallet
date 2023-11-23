package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Balance;
import br.com.invillia.cdb.wallet.domain.Customer;
import br.com.invillia.cdb.wallet.domain.Paper;
import br.com.invillia.cdb.wallet.domain.Wallet;
import br.com.invillia.cdb.wallet.exception.WalletException;
import br.com.invillia.cdb.wallet.persistence.entities.WalletEntity;
import br.com.invillia.cdb.wallet.persistence.repositories.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private PaperService paperService;

    @Mock
    private BalanceService balanceService;
    @InjectMocks
    private WalletService walletService;

    private Customer customer;
    private String transactionId;

    @BeforeEach
    public void setup() {
        customer = new Customer(
                "Ton",
                "12345678910",
                "ton@mail.com",
                new Balance(UUID.randomUUID().toString(), 10.0)
        );
        transactionId = UUID.randomUUID().toString();

    }

    @DisplayName("Buy CDB for customer and throw not enough balance exception")
    @Test
    public void buyCDBForCustomerAndThrowNotEnoughBalanceException() {
        // given a customer
        // and a transaction id
        // and an amount
        int amount = 10;

        // stubbing paper service behavior
        when(paperService.getPaper(any())).thenReturn(new Paper("paper1", 1.2));

        // when call method buy cdb for customer
        // then exception is thrown
        WalletException exception = assertThrows(
                WalletException.class,
                () -> walletService.buyCDBForCustomer(customer, amount, transactionId)
        );

        assertEquals(exception.getMessage(), "Code wallet-exception-001: Cliente não possui saldo suficiente para comprar CDB.");
    }

    @DisplayName("Buy Cdb for customer successfully")
    @Test
    public void buyCDBForCustomerSuccessfully() {
        // given a customer
        // and a transaction id
        // and an amount
        int amount = 1;


        // stubbing paper service behavior
        when(paperService.getPaper(any())).thenReturn(new Paper("paper1", 1.2));

        // stubbing balance service behavior
        when(balanceService.updateBalance(customer.getBalance(), transactionId))
                .thenReturn(new Balance(customer.getCustomerId(), 8.8));

        // when call method buy cdb for customer
        Wallet wallet = walletService.buyCDBForCustomer(customer, amount, transactionId);

        // it should return a new wallet for customer
        assertEquals(wallet.getCustomerId(), customer.getCustomerId());
        assertEquals(wallet.getPaperId(), "paper1");
        assertEquals(wallet.getAmount(), amount);
    }

    @DisplayName("Sell CDB for customer and throw no cdb paper to sell exception")
    @Test
    public void sellCDBForCustomerAndThrowNoCDBPaperToSellException() {
        // given a customer
        // and a transaction id

        // stubbing wallet repository behavior
        when(walletRepository.findAllByCustomerId(customer.getCustomerId()))
                .thenReturn(List.of());

        // when call method sell cdb for customer
        // then exception will be thrown
        WalletException exception = assertThrows(
                WalletException.class,
                () -> walletService.sellCDBForCustomer(customer, transactionId)
        );

        assertEquals(exception.getMessage(), "Code wallet-exception-002: Cliente não papéis CDB para fazer venda.");
    }

    @DisplayName("Sell CDB for customer successfully")
    @Test
    public void sellCDBForCustomerSuccessfully() {
        // given a customer
        // and a transaction id

        // stubbing wallet repository behavior
        when(walletRepository.findAllByCustomerId(customer.getCustomerId()))
                .thenReturn(List.of(
                        new WalletEntity(customer.getCustomerId(), "paper1", 10),
                        new WalletEntity(customer.getCustomerId(), "paper1", 15)
                ));
        // stubbing paper service behavior
        when(paperService.getPaper(any())).thenReturn(new Paper("paper1", 1.2));

        // stubbing balance service behavior
        when(balanceService.updateBalance(customer.getBalance(), transactionId))
                .thenReturn(new Balance(customer.getCustomerId(), 40.0));

        // when call method sell cdb for customer
        List<Wallet> wallets = walletService.sellCDBForCustomer(customer, transactionId);

        assertEquals(wallets.get(0).getCustomerId(), customer.getCustomerId());
        assertEquals(wallets.get(1).getCustomerId(), customer.getCustomerId());
        assertEquals(wallets.get(0).getPaperId(), "paper1");
        assertEquals(wallets.get(1).getPaperId(), "paper1");
        assertEquals(wallets.get(0).getAmount(), 10);
        assertEquals(wallets.get(1).getAmount(), 15);
        assertEquals(customer.getBalance().getBalance(), 40.0);
    }
}