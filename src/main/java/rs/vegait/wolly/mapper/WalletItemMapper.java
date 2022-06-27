package rs.vegait.wolly.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import rs.vegait.wolly.dao.WalletItemDao;
import rs.vegait.wolly.dto.WalletItemDto;

@Component
public class WalletItemMapper {

    public List<WalletItemDto> toListDto(List<WalletItemDao> listofItemDao) {

        return listofItemDao.stream()
                .map(walletItemDao -> toDto(walletItemDao))
                .collect(Collectors.toList());

    }

    public WalletItemDto toDto(WalletItemDao walletItemDao) {
        WalletItemDto walletItemDto = WalletItemDto.builder()
                .cryptocurrencyId(walletItemDao.getCryptocurrency_id())
                .sum(walletItemDao.getSum())
                .build();
        return walletItemDto;
    }

}
