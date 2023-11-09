package br.com.invillia.cdb.wallet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Paper {

    private String id;
    private Double price;

    public Paper(String id, Double price) {
        this.id = id;
        this.price = price;
    }

}
