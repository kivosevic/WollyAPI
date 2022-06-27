package rs.vegait.wolly.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletItem extends AbstractEntity {
    @ToString.Exclude
    @ManyToOne 
    private Wallet wallet;
    private String cryptocurrencyId;
    private Double amount;

    public WalletItem(String cryptoId, Double value, Wallet wallet) {
        this.cryptocurrencyId = cryptoId;
        this.amount = value;
        this.wallet = wallet;
    }
}
