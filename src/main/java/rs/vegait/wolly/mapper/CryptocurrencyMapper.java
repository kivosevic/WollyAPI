package rs.vegait.wolly.mapper;

import rs.vegait.wolly.dto.GetCryptoListResponseDTO;
import rs.vegait.wolly.models.Cryptocurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CryptocurrencyMapper {
    public static GetCryptoListResponseDTO toGetCryptoListResponseDTOEntity(Cryptocurrency cryptocurrency) {
        return GetCryptoListResponseDTO.builder()
                .id(cryptocurrency.getId())
                .name(cryptocurrency.getName())
                .icon(cryptocurrency.getIcon())
                .valueOfOne(cryptocurrency.getValueOfOne())
                .build();
    }
}
