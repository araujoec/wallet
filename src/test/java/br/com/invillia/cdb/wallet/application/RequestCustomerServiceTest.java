package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Balance;
import br.com.invillia.cdb.wallet.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestCustomerServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RequestCustomerService requestCustomerService;

    private String document;
    private String transactionId;

    private String jsonCustomer;
    private Balance balance;

    private String customerId;

    @BeforeEach
    public void setup() {
        document = "12345678910";
        customerId = UUID.randomUUID().toString();
        transactionId = UUID.randomUUID().toString();
        jsonCustomer = "{\n" +
                "  \"name\": \"Ton\",\n" +
                "  \"document\": " + document + ",\n" +
                "  \"email\": \"ton@gmail.com\",\n" +
                "  \"balance\": {\n" +
                "    \"customerId\": \"" + customerId + "\",\n" +
                "    \"balance\": 0.0\n" +
                "  }\n" +
                "}";
        balance = new Balance(UUID.randomUUID().toString(), 10.0);
    }

    @DisplayName("Get customer successfully")
    @Test
    public void getCustomerSuccessfully() {
        // given a customer document
        Map<String, String> params = new HashMap<>(Collections.singletonMap("document", document));
        // and a transaction id
        params.put("transactionId", transactionId);

        // stubbing rest template behavior
        when(restTemplate
                .getForObject("http://localhost:8080/customer/get?document={document}&transactionId={transactionId}",
                        String.class,
                        params)
        ).thenReturn(jsonCustomer);

        // when call method get customer
        Customer customerFound = requestCustomerService.getCustomer(document, transactionId);

        assertNotNull(customerFound);
        assertEquals(customerFound.getName(), "Ton");
        assertEquals(customerFound.getDocument(), document);
        assertEquals(customerFound.getEmail(), "ton@gmail.com");
        assertEquals(customerFound.getBalance().getCustomerId(), customerId);
        assertEquals(customerFound.getBalance().getBalance(), 0.0);
    }

    @DisplayName("Get customer and throw exception")
    @Test
    public void getCustomerAndThrowException() {
        // given a customer document
        Map<String, String> params = new HashMap<>(Collections.singletonMap("document", document));
        // and a transaction id
        params.put("transactionId", transactionId);

        // stubbing rest template behavior
        when(restTemplate
                .getForObject("http://localhost:8080/customer/get?document={document}&transactionId={transactionId}",
                        String.class,
                        params)
        ).thenReturn("incorrect json");

        // when call method get customer
        // then exception is thrown
        Exception exception = assertThrows(RuntimeException.class, () -> requestCustomerService.getCustomer(document, transactionId));

        assertTrue(exception.getMessage().contains("JsonParseException"));
    }

    @DisplayName("Update balance successfully")
    @Test
    public void updateBalanceSuccessfully() {
        // given a balance
        // and a transaction id

        // stubbing rest template behavior
        when(restTemplate.postForEntity(any(), any(), any())).thenReturn(ResponseEntity.ok(balance));

        // when call method update balance
        Balance updatedBalance = requestCustomerService.updateBalance(balance, transactionId);

        // then balance is updated
        assertNotNull(updatedBalance);
        assertEquals(balance.getCustomerId(), updatedBalance.getCustomerId());
        assertEquals(balance.getBalance(), updatedBalance.getBalance());
    }

}