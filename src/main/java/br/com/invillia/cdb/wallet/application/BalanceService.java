package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Balance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BalanceService {

    @Autowired
    ConsultationService consultationService;

    public Balance updateBalance(Balance balance, String operationId) {
        log.info("Updating balance for customer...");
        return consultationService.updateBalance(balance, operationId);
    }
}
