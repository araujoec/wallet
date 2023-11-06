package br.com.invillia.cdb.wallet.persistence.repositories;

import br.com.invillia.cdb.wallet.persistence.entities.PaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<PaperEntity, Long> {

    public PaperEntity findByDocument(String document);
}
