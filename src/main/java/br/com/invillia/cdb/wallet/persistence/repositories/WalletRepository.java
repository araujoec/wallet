package br.com.invillia.cdb.wallet.persistence.repositories;

import br.com.invillia.cdb.wallet.persistence.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, String> {
    public List<WalletEntity> findAllByCustomerId(String customerId);
}
