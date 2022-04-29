package com.backend.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletItem {
    @ToString.Exclude
    @ManyToOne
    Wallet wallet;
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "uuid", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
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
