package br.com.invillia.cdb.wallet.persistence.repositories;

import br.com.invillia.cdb.wallet.persistence.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, String> {
    public WalletEntity findByCustomerId(String customerId);
}
