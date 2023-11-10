package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Balance;
import br.com.invillia.cdb.wallet.domain.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RequestCustomerService {

    @Autowired
    private RestTemplate restTemplate;

    public Customer getCustomer(String document, String transactionId) {
        String url = "http://localhost:8080/customer/get?document={document}&transactionId={transactionId}";
        Map<String, String> params = new HashMap<>(Collections.singletonMap("document", document));
        params.put("transactionId", transactionId);
        String jsonCustomer = restTemplate.getForObject(url, String.class, params);

        try {
            return new ObjectMapper().readValue(jsonCustomer, Customer.class);
        } catch (JsonProcessingException e) {
            log.warn("[{}] Problem parsing JSON to object: {}", transactionId, jsonCustomer);
            return null;
        }
    }

    public Balance updateBalance(Balance balance, String transactionId) {
        String url = "http://localhost:8080/balance/update";
        HttpHeaders headers = new HttpHeaders();
        headers.set("transactionId", transactionId);
        HttpEntity<Balance> balanceHttpEntity = new HttpEntity<>(balance, headers);

        try {
            return restTemplate.postForEntity(new URI(url), balanceHttpEntity, Balance.class).getBody();
        } catch (URISyntaxException e) {
            log.warn("[{}] Problem with URI syntax: {}", transactionId, url);
            throw new RuntimeException(e);
        }
    }

}
