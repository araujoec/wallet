package br.com.invillia.cdb.wallet.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Wallet {

    private String customerId;
    private String paperId;
    private Integer amount;

    public Wallet(String customerId, String paperId, Integer amount) {
        this.customerId = customerId;
        this.paperId = paperId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "{" +
                "customerId='" + customerId + '\'' +
                ", paperId='" + paperId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
