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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final CustomAuthenticationProvider authenticationProvider;

    @RequestMapping(value = "/getCryptoList", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<GetCryptoListResponseDTO>> getCryptoListController() {
        return ResponseEntity.ok().body(userService.getCryptoList());
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<GetCurrentUserResponseDTO> getCurrentUser() {
        GetCurrentUserResponseDTO currentUser = userService.getCurrentUser();
        return ResponseEntity.ok().body(currentUser);
    }

    @GetMapping("/getWallet")
    public ResponseEntity<GetWalletResponseDTO> getWallet() {
        GetWalletResponseDTO wallet = userService.getWallet();
        return ResponseEntity.ok().body(wallet);
    }

    @PatchMapping("/addMoney")
    public ResponseEntity<?> addMoney(@RequestParam(name = "value") Double value) {
        userService.addMoney(value);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/buyCryptocurrency")
    public ResponseEntity<?> buyCryptocurrency(@RequestParam(name = "cryptoId") UUID cryptoId,
                                               @RequestParam(name = "value") Double value) {
        userService.buyCryptocurrency(cryptoId, value);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/sellCryptocurrency")
    public ResponseEntity<?> sellCryptocurrency(@RequestParam(name = "cryptoId") UUID cryptoId,
                                                @RequestParam(name = "value") Double value) {
        userService.sellCryptocurrency(cryptoId, value);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody @Valid CreateUserRequestDTO userDTO) {
        return new ResponseEntity<>(userService.create(userDTO), HttpStatus.CREATED);
    }

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
