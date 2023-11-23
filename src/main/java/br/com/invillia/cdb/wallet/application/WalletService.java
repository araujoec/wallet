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

import java.util.List;

@Slf4j
@Service
public class WalletService {

    @Autowired
    private PaperService paperService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private WalletRepository walletRepository;


    private Paper getPaper(String paperId) {
        return paperService.getPaper(paperId);
    }

    public Wallet buyCDBForCustomer(Customer customer, Integer amount, String transactionId) {
        Paper paper = getPaper("paper1");
        Double totalPaperPrice = paper.getPrice() * amount;
        log.debug("[{}] Total paper price = {}", transactionId, totalPaperPrice);

        if (customer.hasEnoughBalance(totalPaperPrice)) {

            log.debug("[{}] Creating new wallet for customer...", transactionId);
            WalletEntity walletEntity = new WalletEntity(customer.getCustomerId(), paper.getId(), amount);

            customer.deductFromBalance(totalPaperPrice);
            log.info("[{}] Paper deducted from customer balance", transactionId);

            Balance balanceUpdated = balanceService.updateBalance(customer.getBalance(), transactionId);
            log.info("[{}] Balance updated {}", transactionId, balanceUpdated.toString());

            walletRepository.save(walletEntity);
            log.info("[{}] Wallet saved for customer {}", transactionId, walletEntity.toDomain().toString());
            return walletEntity.toDomain();
        } else {
            log.error("[{}] Customer doesn't have enough balance to buy this amount of paper", transactionId);
            throw new WalletException(WalletEnumException.NOT_ENOUGH_BALANCE);
        }
    }

    public List<Wallet> sellCDBForCustomer(Customer customer, String transactionId) {
        List<WalletEntity> walletEntities = walletRepository.findAllByCustomerId(customer.getCustomerId());
        if (walletEntities.isEmpty()) {
            log.error("[{}] Customer doesn't have any CDB paper to sell", transactionId);
            throw new WalletException(WalletEnumException.NO_CDB_PAPER_TO_SELL);
        } else {
            Double totalPaperPrice = getTotalPaperPrice(walletEntities);
            customer.addToBalance(totalPaperPrice);
            log.info("[{}] Value of papers added to customer balance", transactionId);

            Balance balanceUpdated = balanceService.updateBalance(customer.getBalance(), transactionId);
            log.info("[{}] Balance updated {}", transactionId, balanceUpdated.toString());

            walletEntities.forEach(
                    walletEntity -> walletRepository.delete(walletEntity)
            );
            log.info("[{}] Wallets of customer {} deleted from database", transactionId, customer.getCustomerId());
            return walletEntities.stream().map(WalletEntity::toDomain).toList();
        }
    }

    private Double getTotalPaperPrice(List<WalletEntity> walletEntities) {
        return walletEntities
                .stream()
                .map(w ->
                        w.getAmount() * getPaper(w.getPaperId()).getPrice())
                .reduce(Double::sum)
                .get();
    }

}
