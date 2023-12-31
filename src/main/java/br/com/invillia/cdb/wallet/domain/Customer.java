package br.com.invillia.cdb.wallet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(force = true)
public class Customer {

    private String name;
    private final String document;
    private String email;
    private Balance balance;

    public Customer(String name, String document, String email, Balance balance) {
        this.name = name;
        this.document = document;
        this.email = email;
        this.balance = balance;
    }

    public boolean hasEnoughBalance(Double paper) {
        return balance.getBalance() >= paper;
    }

    public String getCustomerId() {
        return balance.getCustomerId();
    }

    public void deductFromBalance(Double price) {
        balance.deduct(price);
    }

    public void addToBalance(Double price) {
        balance.receive(price);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", " + balance.toString() +
                '}';
    }
}
