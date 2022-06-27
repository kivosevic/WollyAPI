package rs.vegait.wolly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletItemDto {
    
    private String cryptocurrencyId;
    private String name;
    private String abbreviation;
    private String icon;
    private Double valueOfOne;
    private Double sum;
}
