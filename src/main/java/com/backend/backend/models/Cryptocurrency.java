package com.backend.backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Cryptocurrency {
    @Id
    @Column(name = "uuid", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @GeneratedValue(strategy = AUTO)
    private UUID id;
    private String name;
    private String abbreviation;
    private String icon;
    private Double valueOfOne;
}
