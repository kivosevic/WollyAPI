package rs.vegait.wolly.mapper;


import rs.vegait.wolly.dto.GetWalletResponseDTO;
import rs.vegait.wolly.models.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletMapper {
    public GetWalletResponseDTO toGetWalletResponseDTOEntity(Wallet wallet) {
        return GetWalletResponseDTO.builder()
                .totalBalance(wallet.getTotalBalance())
                .cryptocurrencies(wallet.getWalletItems())
                .build();
    }
}
