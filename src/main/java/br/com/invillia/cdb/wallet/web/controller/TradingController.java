package br.com.invillia.cdb.wallet.web.controller;

import br.com.invillia.cdb.wallet.application.TradingService;
import br.com.invillia.cdb.wallet.domain.Wallet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "trading")
@Tag(name = "Trading", description = "API for buying and selling CDB")
public class TradingController {

    @Autowired
    private TradingService tradingService;

    @Operation(summary = "Buy CDB by customer document",
            description = "buy any amount of CBD providing the customer's document")
    @PostMapping(value = "/buy")
    public ResponseEntity<Wallet> buyCDB(
            @RequestParam("document")
            String document,
            @RequestParam("amount")
            @Min(1)
            Integer amount
    ) {
        Wallet wallet = tradingService.buyCDB(document, amount);
        return ResponseEntity.ok(wallet);
    }

    @Operation(summary = "Sell CDB by customer document",
            description = "sell all CBD of customer providing the customer's document")
    @PostMapping(value = "/sell")
    public ResponseEntity<List<Wallet>> sellCDB(
            @RequestParam("document")
            String document
    ) {
        List<Wallet> wallets = tradingService.sellCDB(document);
        return ResponseEntity.ok(wallets);
    }

}
