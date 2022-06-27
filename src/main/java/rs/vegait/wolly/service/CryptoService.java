package rs.vegait.wolly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rs.vegait.wolly.dto.GetCryptoListResponseDTO;
import rs.vegait.wolly.exception.NoSuchElementFoundException;
import rs.vegait.wolly.mapper.CryptocurrencyMapper;
import rs.vegait.wolly.models.Cryptocurrency;
import rs.vegait.wolly.repository.CryptocurrencyRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CryptoService {

    private final CryptocurrencyRepository cryptocurrencyRepository;

    public List<GetCryptoListResponseDTO> getAllCrypto() {
        List<Cryptocurrency> list = cryptocurrencyRepository.findAll();
        return list.stream()
                .map(CryptocurrencyMapper::toGetCryptoListResponseDTOEntity)
                .collect(Collectors.toList());
    }

    public GetCryptoListResponseDTO getCrypto(String id) {
        Cryptocurrency cryptocurrency = cryptocurrencyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Element not found"));
        return CryptocurrencyMapper.toGetCryptoListResponseDTOEntity(cryptocurrency);
    }

}
