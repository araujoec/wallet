package br.com.invillia.cdb.wallet.persistence.entities;

import br.com.invillia.cdb.wallet.domain.Paper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paper")
@Getter
@Setter
@NoArgsConstructor
public class PaperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document")
    private String document;

    @Column(name = "amount")
    private Long amount;

    public PaperEntity(String customerDocument, Long amount) {
        this.document = customerDocument;
        this.amount = amount;
    }

    public static PaperEntity fromDomain(Paper paper) {
        return new PaperEntity(
                paper.getCustomerDocument(),
                paper.getAmount()
        );
    }
}
