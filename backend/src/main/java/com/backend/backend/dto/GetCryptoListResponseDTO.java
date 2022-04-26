package com.backend.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCryptoListResponseDTO {
    private String name;
    private String icon;
    private Double valueOfOne;
}
