package com.backend.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@MappedSuperclass
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
@Setter
@Getter
public class AbstractEntity {
    @Id
    @Column(name = "uuid", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @GeneratedValue(strategy = AUTO)
    protected UUID id;
}
