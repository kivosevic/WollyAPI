package com.backend.backend.mapper;


import com.backend.backend.dto.GetWalletResponseDTO;
import com.backend.backend.models.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletMapper {
    public GetWalletResponseDTO toGetWalletResponseDTOEntity(Wallet wallet){
        return GetWalletResponseDTO.builder()
                .totalBalance(wallet.getTotalBalance())
                .cryptocurrencies(wallet.getWalletItems())
                .build();
    }
}
