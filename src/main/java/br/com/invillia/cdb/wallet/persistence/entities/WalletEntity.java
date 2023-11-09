package br.com.invillia.cdb.wallet.persistence.entities;

import br.com.invillia.cdb.wallet.domain.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@NoArgsConstructor
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "paper_id", nullable = false)
    private String paperId;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public WalletEntity(String customerId, String paperId, Integer amount) {
        this.customerId = customerId;
        this.paperId = paperId;
        this.amount = amount;
    }

    public Wallet toDomain() {
        return new Wallet(
                this.customerId,
                this.paperId,
                this.amount
        );
    }
}
