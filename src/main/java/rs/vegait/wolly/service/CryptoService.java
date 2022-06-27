package rs.vegait.wolly.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rs.vegait.wolly.dto.CryptoDto;
import rs.vegait.wolly.dto.GetCryptoListResponseDTO;
import rs.vegait.wolly.exception.NoSuchElementFoundException;
import rs.vegait.wolly.mapper.CryptocurrencyMapper;
import rs.vegait.wolly.models.Cryptocurrency;
import rs.vegait.wolly.repository.CryptocurrencyRepository;

@Service
@RequiredArgsConstructor
public class CryptoService {

    private final CryptocurrencyRepository cryptocurrencyRepository;

    public void addAllCryptoInDatabase(List<CryptoDto> result) {

        for (int i = 0; i < 20; i++) {
            CryptoDto cryptoDto = result.get(i);
            cryptoDto.setLogo(requestUrlToBase64(cryptoDto.getLogo()));

            Cryptocurrency cryptocurrency = CryptocurrencyMapper.toEntity(cryptoDto);

            cryptocurrencyRepository.save(cryptocurrency);
        }

    }

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

    private static String requestUrlToBase64(String imgUrl) {
        String result = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(imgUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            InputStream inputStream = connection.getInputStream();

            int len = -1;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            result = new String(Base64.getEncoder().encode(out.toByteArray()));
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

}
