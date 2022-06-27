package rs.vegait.wolly.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends AbstractEntity {

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
