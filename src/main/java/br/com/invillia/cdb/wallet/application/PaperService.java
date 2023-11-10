package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Paper;
import br.com.invillia.cdb.wallet.exception.PaperException;
import br.com.invillia.cdb.wallet.exception.enums.PaperEnumException;
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
        if (paperRepository.findById(paperId).isPresent()) {
            return paperRepository.findById(paperId).get().toDomain();
        } else {
            log.warn("No paper found for id = {}", paperId);
            throw new PaperException(PaperEnumException.PAPER_NOT_FOUND);
        }
    }

}
