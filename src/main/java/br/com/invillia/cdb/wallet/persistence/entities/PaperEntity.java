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
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "price", nullable = false)
    private Double price;

    public PaperEntity(String id, Double price) {
        this.id = id;
        this.price = price;
    }

    public Paper toDomain() {
        return new Paper(id, price);
    }
}
