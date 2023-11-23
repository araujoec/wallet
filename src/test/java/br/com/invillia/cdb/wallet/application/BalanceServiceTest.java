package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private RequestCustomerService requestCustomerService;

    @InjectMocks
    private BalanceService balanceService;

    private Balance balance;
    private String transactionId;

    @BeforeEach
    public void setup() {
        balance = new Balance(UUID.randomUUID().toString(), 10.0);
        transactionId = UUID.randomUUID().toString();
    }

    @DisplayName("Update balance")
    @Test
    public void updateBalance() {
        // given a balance
        // and a transaction id

        // stubbing request custom service behavior
        when(requestCustomerService.updateBalance(balance, transactionId))
                .thenReturn(balance);

        Balance balanceUpdated = balanceService.updateBalance(balance, transactionId);

        assertNotNull(balanceUpdated);
        assertEquals(balanceUpdated.getBalance(), balance.getBalance());
        assertEquals(balanceUpdated.getCustomerId(), balance.getCustomerId());
    }
}