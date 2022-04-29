package com.backend.backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;
}
