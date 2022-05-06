package rs.vegait.wolly.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletItem extends AbstractEntity {
    @ToString.Exclude
    @ManyToOne
    private Wallet wallet;

    @Column(name = "cryptocurrencyId", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID cryptocurrencyId;
    private Double amount;

    public WalletItem(UUID cryptoId, Double value, Wallet wallet) {
        this.cryptocurrencyId = cryptoId;
        this.amount = value;
        this.wallet = wallet;
    }
}
