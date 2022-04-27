package com.backend.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    //@GeneratedValue(strategy = AUTO)
    private String id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<WalletItem> cryptocurrencies;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    private Double totalBalance;
}

