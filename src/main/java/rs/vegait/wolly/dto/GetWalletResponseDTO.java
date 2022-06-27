package rs.vegait.wolly.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetWalletResponseDTO {
    private List<WalletItemDto> cryptocurrencies;
    private Double totalBalance;
}
