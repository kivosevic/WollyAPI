package rs.vegait.wolly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetCryptoListResponseDTO {
    private String id;
    private String name;
    private String icon;
    private Double valueOfOne;
}
