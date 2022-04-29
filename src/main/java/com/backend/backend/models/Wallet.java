package com.backend.backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Wallet {
    @Id
    @Column(name = "uuid", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @GeneratedValue(strategy = AUTO)
    private UUID id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wallet")
    private List<WalletItem> walletItems = new ArrayList<>();
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    private Double totalBalance = 0d;
}

