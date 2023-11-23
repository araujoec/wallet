package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Paper;
import br.com.invillia.cdb.wallet.exception.PaperException;
import br.com.invillia.cdb.wallet.exception.enums.PaperEnumException;
import br.com.invillia.cdb.wallet.persistence.entities.PaperEntity;
import br.com.invillia.cdb.wallet.persistence.repositories.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    private List<Paper> papers;

    public Paper getPaper(String paperId) {
        if (papers == null) {
            papers = getPapers();
        }
        Optional<Paper> paper = papers.stream().filter(p -> p.getId().equalsIgnoreCase(paperId)).findFirst();
        if (paper.isPresent()) {
            return paper.get();
        } else {
            log.error("No paper found for id = {}", paperId);
            throw new PaperException(PaperEnumException.PAPER_NOT_FOUND);
        }
    }

    private List<Paper> getPapers() {
        log.debug("Getting all papers in database...");
        List<Paper> papers = paperRepository.findAll().stream().map(PaperEntity::toDomain).toList();
        if (papers.isEmpty()) {
            log.error("None paper found in database");
            throw new PaperException(PaperEnumException.NONE_PAPER_IN_DATABASE);
        } else {
            return papers;
        }
    }
}
