package com.backend.backend.dto;

import com.backend.backend.models.WalletItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetWalletResponseDTO {
    private List<WalletItem> cryptocurrencies;
    private Double totalBalance;
}
