package br.com.invillia.cdb.wallet.web.controller;

import br.com.invillia.cdb.wallet.application.TradingService;
import br.com.invillia.cdb.wallet.domain.Wallet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "trading")
@Tag(name = "Trading", description = "API for buying and selling CDB")
public class TradingController {

    @Autowired
    private TradingService tradingService;

    public TradingController(TradingService tradingService) {
        this.tradingService = tradingService;
    }

    @Operation(summary = "Buy CDB by customer document",
            description = "buy any amount of CBD providing the customer's document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CBD purchased successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Insufficient balance to buy CBD")
    })
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

}
