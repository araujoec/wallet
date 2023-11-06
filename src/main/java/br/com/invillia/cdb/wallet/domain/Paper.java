package br.com.invillia.cdb.wallet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Setter
@NoArgsConstructor
public class Paper {

    private String customerDocument;

    private long amount;

    public Paper(String customerDocument, long amount) {
        this.customerDocument = customerDocument;
        this.amount = amount;
    }

}
