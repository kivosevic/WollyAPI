package rs.vegait.wolly.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cryptocurrency  {
    @Id
    private String id;
    private String name;
    private String abbreviation;
    @Column(length = 100000)
    private String icon;
    private Double valueOfOne;
}
