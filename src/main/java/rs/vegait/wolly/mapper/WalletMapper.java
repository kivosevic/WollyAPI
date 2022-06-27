package rs.vegait.wolly.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import rs.vegait.wolly.dto.GetWalletResponseDTO;
import rs.vegait.wolly.dto.WalletItemDto;
import rs.vegait.wolly.models.Wallet;

@Component
@RequiredArgsConstructor
public class WalletMapper {
    public GetWalletResponseDTO toGetWalletResponseDTOEntity(Wallet wallet, List<WalletItemDto> list) {
        return GetWalletResponseDTO.builder()
                .totalBalance(wallet.getTotalBalance())
                .cryptocurrencies(list)
                .build();
    }
}
