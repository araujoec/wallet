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

    public void updateCDB(Paper paper) {
        log.info("Here too on Paper Service");
        PaperEntity paperEntity = paperRepository.findByDocument(paper.getCustomerDocument());
        log.info("passou daqui?");

        if (paperEntity == null) {
            log.info("this paper doesn't exist in db");
            paperEntity = PaperEntity.fromDomain(paper);
        }else{
            paperEntity.setAmount(paper.getAmount());
        }

        log.info("saving paper to db");
        paperRepository.save(paperEntity);
    }

}
