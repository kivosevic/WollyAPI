package com.backend.backend.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cryptocurrency {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;
    private String name;
    private String abbreviation;
    private String icon;
    private Double value;
}
