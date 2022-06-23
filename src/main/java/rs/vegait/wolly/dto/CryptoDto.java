package rs.vegait.wolly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CryptoDto {
    
    @JsonProperty("id")
    private String id;
    @JsonProperty("image")
    private String logo;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("current_price")
    private String price;
    @JsonProperty("name")
    private String name;

    
}
