package rs.vegait.wolly.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import rs.vegait.wolly.dto.CryptoDto;
import rs.vegait.wolly.mapper.CryptocurrencyMapper;
import rs.vegait.wolly.models.Cryptocurrency;
import rs.vegait.wolly.repository.CryptocurrencyRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RapidApiService {

    private final CryptocurrencyRepository cryptocurrencyRepository;
    @Value("${rapid.api.key}")
    private String rapidApiKey;

    @Async
    public void loadDataFromApi() throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("X-RapidAPI-Key", rapidApiKey);
        requestHeader.add("X-RapidAPI-Host", "coingecko.p.rapidapi.com");
        String url = "https://coingecko.p.rapidapi.com/coins/markets?vs_currency=usd&page=1&per_page=100&order=market_cap_desc";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeader);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        List<CryptoDto> result = new ObjectMapper().readValue(response.getBody(),
                new TypeReference<ArrayList<CryptoDto>>() {
                });
        addAllCryptoInDatabase(result);

    }

    public void addAllCryptoInDatabase(List<CryptoDto> result) {

        for (int i = 0; i < 20; i++) {
            CryptoDto cryptoDto = result.get(i);
            cryptoDto.setLogo(requestUrlToBase64(cryptoDto.getLogo()));
            cryptoDto.setId(cryptoDto.getId().trim());
            Cryptocurrency cryptocurrency = CryptocurrencyMapper.toEntity(cryptoDto);
            cryptocurrencyRepository.save(cryptocurrency);

        }

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
