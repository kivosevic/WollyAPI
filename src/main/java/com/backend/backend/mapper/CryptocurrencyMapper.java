package com.backend.backend.mapper;

import com.backend.backend.dto.GetCryptoListResponseDTO;
import com.backend.backend.models.Cryptocurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CryptocurrencyMapper {
    public static GetCryptoListResponseDTO toGetCryptoListResponseDTOEntity(Cryptocurrency cryptocurrency) {
        return GetCryptoListResponseDTO.builder()
                .name(cryptocurrency.getName())
                .icon(cryptocurrency.getIcon())
                .valueOfOne(cryptocurrency.getValueOfOne())
                .build();
    }
}
