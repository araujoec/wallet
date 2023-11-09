package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Customer;
import br.com.invillia.cdb.wallet.domain.Wallet;
import br.com.invillia.cdb.wallet.exception.TradingException;
import br.com.invillia.cdb.wallet.exception.enums.TradingEnumException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class TradingService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private ConsultationService consultationService;

    public Wallet buyCDB(String document, Integer amount) {
        String operationId = UUID.randomUUID().toString();
        log.info("Operation id: {}", operationId);

        if (amount <= 0) {
            log.warn("Quantidade de paper não aceitável. {}", amount);
            throw new TradingException(TradingEnumException.AMOUNT_EQUAL_OR_LOWER_THAN_ZERO);
        }

        Customer customer = consultationService.getCustomer(document, operationId);

        if (customer == null) {
            log.warn("Customer não encontrado para documento {}.", document);
            throw new TradingException(TradingEnumException.CUSTOMER_NOT_FOUND);
        } else {
            return walletService.buyCDBForCustomer(customer, amount, operationId);
        }
    }
}
