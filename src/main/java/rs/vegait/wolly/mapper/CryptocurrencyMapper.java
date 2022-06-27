package rs.vegait.wolly.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import rs.vegait.wolly.dto.CryptoDto;
import rs.vegait.wolly.dto.GetCryptoListResponseDTO;
import rs.vegait.wolly.models.Cryptocurrency;

@Component
@RequiredArgsConstructor
public class CryptocurrencyMapper {
    public static GetCryptoListResponseDTO toGetCryptoListResponseDTOEntity(Cryptocurrency cryptocurrency) {
        return GetCryptoListResponseDTO.builder()
                .id(cryptocurrency.getId())
                .name(cryptocurrency.getName())
                .icon(cryptocurrency.getIcon())
                .abbreviation(cryptocurrency.getAbbreviation())
                .valueOfOne(cryptocurrency.getValueOfOne())
                .build();
    }

    public static Cryptocurrency toEntity(CryptoDto cryptoDto) {
        return Cryptocurrency.builder()
                .id(cryptoDto.getId())
                .icon(cryptoDto.getLogo())
                .name(cryptoDto.getName())
                .abbreviation(cryptoDto.getSymbol())
                .valueOfOne(Double.parseDouble(cryptoDto.getPrice()))
                .build();
    }
}
