package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Balance;
import br.com.invillia.cdb.wallet.domain.Customer;
import br.com.invillia.cdb.wallet.domain.Paper;
import br.com.invillia.cdb.wallet.domain.Wallet;
import br.com.invillia.cdb.wallet.exception.WalletException;
import br.com.invillia.cdb.wallet.exception.enums.WalletEnumException;
import br.com.invillia.cdb.wallet.persistence.entities.WalletEntity;
import br.com.invillia.cdb.wallet.persistence.repositories.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WalletService {

    @Autowired
    private PaperService paperService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    WalletRepository walletRepository;

    private Paper getPaper() {
        return paperService.getPaper("tortuga");
    }

    public Wallet buyCDBForCustomer(Customer customer, Integer amount, String transactionId) {
        Double totalPaperPrice = getPaper().getPrice() * amount;
        log.debug("[{}] Total paper price = {}", transactionId, totalPaperPrice);

        if (customer.hasEnoughBalance(totalPaperPrice)) {
            WalletEntity walletEntity = walletRepository.findByCustomerId(customer.getCustomerId());

            if (walletEntity == null) {
                log.debug("[{}] Creating new wallet for customer...", transactionId);
                walletEntity = new WalletEntity(customer.getCustomerId(), getPaper().getId(), amount);
            }
            log.debug("[{}] Wallet: {}", transactionId, walletEntity);

            customer.deductFromBalance(totalPaperPrice);
            log.info("[{}] Paper deducted from customer balance", transactionId);

            Balance balanceUpdated = balanceService.updateBalance(customer.getBalance(), transactionId);
            log.info("[{}] Balance updated {}", transactionId, balanceUpdated.toString());

            walletRepository.save(walletEntity);
            log.info("[{}] Wallet saved for customer {}", transactionId, walletEntity.toDomain().toString());
            return walletEntity.toDomain();
        } else {
            log.warn("[{}] Customer doesn't have enough balance to buy this amount of paper", transactionId);
            throw new WalletException(WalletEnumException.NOT_ENOUGH_BALANCE);
        }
    }

    public Wallet sellCDBForCustomer(Customer customer, String transactionId) {
        WalletEntity walletEntity = walletRepository.findByCustomerId(customer.getCustomerId());
        if (walletEntity == null) {
            log.warn("[{}] Customer doesn't have any CDB paper to sell", transactionId);
            throw new WalletException(WalletEnumException.NO_CDB_PAPER_TO_SELL);
        } else {
            Double totalPaperPrice = walletEntity.getAmount() * getPaper().getPrice();
            customer.addToBalance(totalPaperPrice);
            log.info("[{}] Value of papers added to customer balance", transactionId);

            Balance balanceUpdated = balanceService.updateBalance(customer.getBalance(), transactionId);
            log.info("[{}] Balance updated {}", transactionId, balanceUpdated.toString());

            walletRepository.delete(walletEntity);
            log.info("[{}] Wallet deleted from database {}", transactionId, walletEntity.toDomain().toString());
            return walletEntity.toDomain();
        }
    }
}
