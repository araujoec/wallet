package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Customer;
import br.com.invillia.cdb.wallet.domain.Wallet;
import br.com.invillia.cdb.wallet.exception.TradingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradingServiceTest {

    @Mock
    private WalletService walletService;

    @Mock
    private RequestCustomerService requestCustomerService;

    @InjectMocks
    private TradingService tradingService;

    private String document;

    @BeforeEach
    public void setup() {
        document = "12345678910";
    }

    @DisplayName("Buy CDB and throw amount equal or lower than zero exception")
    @Test
    public void buyCDBAndThrowAmountEqualOrLowerThanZeroException() {
        // given a customer document
        // and an amount equal to zero
        Integer amount = 0;

        // when call method buy cdb
        // then exception will be thrown
        TradingException exception = assertThrows(
                TradingException.class,
                () -> tradingService.buyCDB(document, amount)
        );

        assertEquals(exception.getMessage(), "Code trading-exception-001: Quantidade de papéis menor ou igual a zero.");
    }

    @DisplayName("Buy CDB and throw customer not found exception")
    @Test
    public void buyCDBAndThrowCustomerNotFoundException() {
        // given a customer document
        // and an amount
        Integer amount = 1;

        // stubbing request customer service behavior
        when(requestCustomerService.getCustomer(eq(document), any()))
                .thenReturn(null);

        // when call method buy cdb
        // then exception will be thrown
        TradingException exception = assertThrows(
                TradingException.class,
                () -> tradingService.buyCDB(document, amount)
        );

        assertEquals(exception.getMessage(), "Code trading-exception-002: Cliente não encontrado no banco de dados.");
    }

    @DisplayName("Buy CDB successfully")
    @Test
    public void buyCDBSuccessfully() {
        // given a customer document
        // and an amount
        Integer amount = 1;

        // stubbing request customer service behavior
        when(requestCustomerService.getCustomer(eq(document), any()))
                .thenReturn(new Customer("Name", document, "name@mail.com", null));

        // stubbing wallet service behavior
        when(walletService.buyCDBForCustomer(any(), any(), any()))
                .thenReturn(new Wallet("customerId", "paperId", amount));

        // when call method buy cdb
        // then new wallet is created
        Wallet wallet = tradingService.buyCDB(document, amount);
    }

    @DisplayName("Sell CDB and throw customer not found exception")
    @Test
    public void sellCDBAndThrowCustomerNotFoundException() {
        // given a customer document

        // stubbing request customer service behavior
        when(requestCustomerService.getCustomer(eq(document), any()))
                .thenReturn(null);

        // when call method sell cdb
        // then exception will be thrown
        TradingException exception = assertThrows(
                TradingException.class,
                () -> tradingService.sellCDB(document)
        );

        assertEquals(exception.getMessage(), "Code trading-exception-002: Cliente não encontrado no banco de dados.");
    }

    @DisplayName("Sell CDB successfully")
    @Test
    public void sellCDBSuccessfully() {
        // given a customer document

        // stubbing request customer service behavior
        when(requestCustomerService.getCustomer(eq(document), any()))
                .thenReturn(new Customer("Name", document, "name@mail.com", null));

        // stubbing wallet service behavior
        when(walletService.sellCDBForCustomer(any(), any()))
                .thenReturn(List.of(new Wallet("customerId", "paperId", 1)));

        // when call method sell cdb
        // then list of deleted wallet is returned
        List<Wallet> wallets = tradingService.sellCDB(document);
    }
}