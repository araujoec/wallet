package br.com.invillia.cdb.wallet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(force = true)
public class Balance {

    private final String customerId;
    private Double balance;

    public Balance(String customerId, Double balance) {
        this.customerId = customerId;
        this.balance = balance;
    }

    void deduct(Double price) {
        balance -= price;
    }

    void receive(Double price) {
        balance += price;
    }

    @Override
    public String toString() {
        return "{" +
                "customerId='" + customerId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
