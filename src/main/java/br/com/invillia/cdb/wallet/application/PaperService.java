package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Paper;
import br.com.invillia.cdb.wallet.persistence.entities.PaperEntity;
import br.com.invillia.cdb.wallet.persistence.repositories.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    public Paper getPaper(String paperId) {
        log.info("Getting paper...");
        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow();

        return paperEntity.toDomain();
    }

}
