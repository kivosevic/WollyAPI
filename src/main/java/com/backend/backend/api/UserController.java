package com.backend.backend.api;

import com.backend.backend.dto.CreateUserRequestDTO;
import com.backend.backend.dto.GetCryptoListResponseDTO;
import com.backend.backend.dto.GetCurrentUserResponseDTO;
import com.backend.backend.dto.GetWalletResponseDTO;
import com.backend.backend.security.CustomAuthenticationProvider;
import com.backend.backend.security.JwtRequest;
import com.backend.backend.security.JwtResponse;
import com.backend.backend.security.TokenProvider;
import com.backend.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Api(tags = "Users")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final CustomAuthenticationProvider authenticationProvider;

    @ApiOperation(value = "This method is used to get information about user's cryptocurrencies.")
    @GetMapping("/crypto")
    public ResponseEntity<List<GetCryptoListResponseDTO>> getCryptoListController() {
        return ResponseEntity.ok().body(userService.getCryptoList());
    }

    @ApiOperation(value = "This method is used to get information about currently logged in user.")
    @GetMapping("/current")
    public ResponseEntity<GetCurrentUserResponseDTO> getCurrentUser() {
        GetCurrentUserResponseDTO currentUser = userService.getCurrentUser();
        return ResponseEntity.ok().body(currentUser);
    }

    @ApiOperation(value = "This method is used to get information about user's wallet.")
    @GetMapping("/wallet")
    public ResponseEntity<GetWalletResponseDTO> getWallet() {
        GetWalletResponseDTO wallet = userService.getWallet();
        return ResponseEntity.ok().body(wallet);
    }

    @ApiOperation(value = "This method is used to add money to user's credit card.")
    @PatchMapping("/addMoney")
    public ResponseEntity<?> addMoney(@RequestParam(name = "value") Double value) {
        userService.addMoney(value);
        return ResponseEntity.ok("success");
    }

    @ApiOperation(value = "This method is used to buy wanted cryptocurrency.")
    @PatchMapping("/buyCryptocurrency")
    public ResponseEntity<?> buyCryptocurrency(@RequestParam(name = "cryptoId") UUID cryptoId,
                                               @RequestParam(name = "value") Double value) {
        userService.buyCryptocurrency(cryptoId, value);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "This method is used to sell wanted cryptocurrency.")
    @PatchMapping("/sellCryptocurrency")
    public ResponseEntity<?> sellCryptocurrency(@RequestParam(name = "cryptoId") UUID cryptoId,
                                                @RequestParam(name = "value") Double value) {
        userService.sellCryptocurrency(cryptoId, value);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "This method is used to register a new user.")
    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody @Valid CreateUserRequestDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDTO));
    }

    @ApiOperation(value = "This method is used to get logged in.")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(),
                jwtRequest.getPassword());
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (userService.findByEmail(jwtRequest.getEmail()) == null) {
            throw new SecurityException("Provided email is not registered.");
        }
        String jwt = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
