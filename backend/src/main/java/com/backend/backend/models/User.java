package com.backend.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    @Id
    @Column(name = "uuid", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @GeneratedValue(strategy = AUTO)
    private UUID id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Double currentCardBalance;
    private String role;
    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;
    @OneToMany
    private List<Cryptocurrency> currencyList;
}
