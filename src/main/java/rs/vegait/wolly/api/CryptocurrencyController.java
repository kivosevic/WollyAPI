package rs.vegait.wolly.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import rs.vegait.wolly.dto.CryptoDto;
import rs.vegait.wolly.dto.GetCryptoListResponseDTO;
import rs.vegait.wolly.service.CryptoService;

@RestController
@RequestMapping("/api/v1/cryptos")
@Api(tags = "Cryptocurrencies")
@RequiredArgsConstructor
public class CryptocurrencyController {

    private final CryptoService cryptoService;

    @GetMapping("/live")
    public ResponseEntity<?> getLiveCrypto() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://coingecko.p.rapidapi.com/coins/markets?vs_currency=usd&page=1&per_page=100&order=market_cap_desc"))
                .header("X-RapidAPI-Key", "")
                .header("X-RapidAPI-Host", "coingecko.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        List<CryptoDto> result = new ObjectMapper().readValue(response.body(),
                new TypeReference<ArrayList<CryptoDto>>() {
                });

        cryptoService.addAllCryptoInDatabase(result);

        return ResponseEntity.ok("Data is in database");
    }

    @GetMapping
    public ResponseEntity<?> getAllCrypto() throws IOException, InterruptedException {

        List<GetCryptoListResponseDTO> list = cryptoService.getAllCrypto();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCrypto(@PathVariable("id") String id) {
        return ResponseEntity.ok(cryptoService.getCrypto(id));
    }

}
