package com.backend.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Double currentCardBalance;
    private String role;

    @OneToMany
    private List<Cryptocurrency> currencyList;

    private String userName;
    /*public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userName = email;
        this.currentCardBalance = Double.valueOf(50000);
    }*/
}
