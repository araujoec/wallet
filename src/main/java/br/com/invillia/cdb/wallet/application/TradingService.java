package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Paper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TradingService {

    @Autowired
    private PaperService paperService;

    public void persistTrading(Paper paper) {
        log.info("I was here!");
        paperService.updateCDB(paper);
    }
}
