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

//    @Autowired
//    private PaperService paperService;

    @Autowired
    private BalanceService balanceService;

//    private final Paper paper = paperService.getPaper("invillia2023");

    private final Paper paper = new Paper("invillia2023", 1.2);
    @Autowired
    WalletRepository walletRepository;

    public Wallet buyCDBForCustomer(Customer customer, Integer amount, String operationId) {
        Double totalPaperPrice = paper.getPrice() * amount;

        if (customer.hasEnoughBalance(totalPaperPrice)) {
            WalletEntity walletEntity = walletRepository.findByCustomerId(customer.getBalance().getCustomerId());
            if (walletEntity == null) {
                walletEntity = new WalletEntity(customer.getBalance().getCustomerId(), paper.getId(), amount);
            }

            customer.getBalance().discountCDB(totalPaperPrice);
            log.info("Discounting paper from customer balance.");

            Balance balanceUpdated = balanceService.updateBalance(customer.getBalance(), operationId);
            log.info("Balance updated {}", balanceUpdated);

            walletRepository.save(walletEntity);
            log.info("Wallet saved for customer {}", walletEntity.toDomain().toString());
            return walletEntity.toDomain();
        } else {
            log.warn("[{}] Customer doesn't have enough balance to buy this amount of paper.", operationId);
            throw new WalletException(WalletEnumException.NOT_ENOUGH_BALANCE);
        }
    }

    public Wallet sellCDBForCustomer(Customer customer, String operationId) {
        WalletEntity walletEntity = walletRepository.findByCustomerId(customer.getBalance().getCustomerId());
        if (walletEntity == null) {
            log.info("Customer doesn't have any CDB paper to sell.");
            throw new WalletException(WalletEnumException.NO_CDB_PAPER_TO_SELL);
        } else {
            Double totalPaperPrice = walletEntity.getAmount() * paper.getPrice();
            customer.getBalance().receiveCDB(totalPaperPrice);
            log.info("Receiving CDB paper sold.");

            Balance balanceUpdated = balanceService.updateBalance(customer.getBalance(), operationId);
            log.info("Balance updated {}", balanceUpdated);

            walletRepository.delete(walletEntity);
            log.info("Wallet deleted from database {}", walletEntity.toDomain().toString());
            return walletEntity.toDomain();
        }
    }
}
