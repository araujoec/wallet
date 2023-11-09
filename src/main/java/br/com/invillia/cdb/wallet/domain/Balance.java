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

    public void discountCDB(Double price) {
        balance -= price;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "customerId='" + customerId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
