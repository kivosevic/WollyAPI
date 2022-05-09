package rs.vegait.wolly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class GetCryptoListResponseDTO {
    private String name;
    private String icon;
    private Double valueOfOne;
}
