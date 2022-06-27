package rs.vegait.wolly.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import rs.vegait.wolly.dto.GetCryptoListResponseDTO;
import rs.vegait.wolly.service.CryptoService;
import rs.vegait.wolly.service.RapidApiService;

@RestController
@RequestMapping("/api/v1/cryptos")
@Api(tags = "Cryptocurrencies")
@RequiredArgsConstructor
public class CryptocurrencyController {

    private final CryptoService cryptoService;
    private final RapidApiService rapidApiService;

    

    @GetMapping
    public ResponseEntity<?> getAllCrypto() throws IOException, InterruptedException {

        List<GetCryptoListResponseDTO> list = cryptoService.getAllCrypto();
        rapidApiService.loadDataFromApi();
        return ResponseEntity.ok(list);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCrypto(@PathVariable("id") String id) {
        return ResponseEntity.ok(cryptoService.getCrypto(id));
    }
}
