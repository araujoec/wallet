package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Customer;
import br.com.invillia.cdb.wallet.domain.Wallet;
import br.com.invillia.cdb.wallet.exception.TradingException;
import br.com.invillia.cdb.wallet.exception.enums.TradingEnumException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TradingService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private RequestCustomerService requestCustomerService;

    public Wallet buyCDB(String document, Integer amount) {
        String transactionId = UUID.randomUUID().toString();
        log.debug("Buying CDB for customer: document = {}, amount = {}", document, amount);
        log.trace("Transaction id: {}", transactionId);

        if (amount <= 0) {
            log.warn("[{}] Paper amount equal or lower than zero ({})", transactionId, amount);
            throw new TradingException(TradingEnumException.AMOUNT_EQUAL_OR_LOWER_THAN_ZERO);
        }

        log.debug("[{}] Searching for customer in customer microservice...", transactionId);
        Customer customer = requestCustomerService.getCustomer(document, transactionId);

        if (customer == null) {
            log.error("[{}] Customer not found for document {}", transactionId, document);
            throw new TradingException(TradingEnumException.CUSTOMER_NOT_FOUND);
        } else {
            log.debug("[{}] Customer found: {}", transactionId, customer);
            return walletService.buyCDBForCustomer(customer, amount, transactionId);
        }
    }

    public List<Wallet> sellCDB(String document) {
        String transactionId = UUID.randomUUID().toString();
        log.debug("Selling CDBs of customer: document = {}", document);
        log.trace("Transaction id: {}", transactionId);

        Customer customer = requestCustomerService.getCustomer(document, transactionId);
        if (customer == null) {
            log.warn("[{}] Customer not found for document {}", transactionId, document);
            throw new TradingException(TradingEnumException.CUSTOMER_NOT_FOUND);
        } else {
            log.debug("[{}] Customer found: {}", transactionId, customer);
            return walletService.sellCDBForCustomer(customer, transactionId);
        }
    }
}
