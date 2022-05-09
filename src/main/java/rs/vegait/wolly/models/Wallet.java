package rs.vegait.wolly.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Wallet extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wallet")
    private List<WalletItem> walletItems = new ArrayList<>();
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    private Double totalBalance = 0d;
}

