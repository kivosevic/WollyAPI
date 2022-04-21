package com.backend.backend.api;
import com.backend.backend.domain.Cryptocurrency;
import com.backend.backend.domain.User;
import com.backend.backend.dto.CreateUserRequestDTO;
import com.backend.backend.mapper.UserMapper;
import com.backend.backend.security.CustomAuthenticationProvider;
import com.backend.backend.security.JwtRequest;
import com.backend.backend.security.JwtResponse;
import com.backend.backend.security.TokenProvider;
import com.backend.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final CustomAuthenticationProvider authenticationProvider;
    private final UserMapper userMapper;

    private String getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return "";
    }

    @GetMapping("/getCryptoList")
    public ResponseEntity<List<Cryptocurrency>> getCryptoList(){
        String username = getLoggedInUser();
        if(username == ""){
            throw new UsernameNotFoundException("");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/getCryptoList").toUriString());
        return ResponseEntity.ok().body(userService.getCryptoList(username));
    }

    @GetMapping("/currentUserMoney")
    public ResponseEntity<Double> currentUserMoney(){
        String username = getLoggedInUser();
        if(username == ""){
            throw new UsernameNotFoundException("");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/currentUserMoney").toUriString());
        return ResponseEntity.ok().body(userService.getUsersMoney(username));
    }

    @PatchMapping("/addMoney")
    public ResponseEntity<?> addMoney(@RequestParam(name="value") Double value){
        String username = getLoggedInUser();
        if(username == ""){
            throw new UsernameNotFoundException("");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/addMoney").toUriString());
        userService.addMoney(username, value);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/buyCryptocurrency")
    public ResponseEntity<?> buyCryptocurrency(@RequestParam(name="cryptoName") String cryptoName,
                                               @RequestParam(name="value") Double value){
        String username = getLoggedInUser();
        if(username == ""){
            throw new UsernameNotFoundException("");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/buyCryptocurrency").toUriString());
        userService.buyCryptocurrency(username, cryptoName,value);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/sellCryptocurrency")
    public ResponseEntity<?> sellCryptocurrency(@RequestParam(name="cryptoName") String cryptoName,
                                                @RequestParam(name="value") Double value){
        String username = getLoggedInUser();
        if(username == ""){
            throw new UsernameNotFoundException("");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/sellCryptocurrency").toUriString());
        userService.sellCryptocurrency(username, cryptoName,value);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequestDTO userDTO) {
        return new ResponseEntity<>(userService.create(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest jwtRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(),
                                                                                                          jwtRequest.getPassword());
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(userService.findByEmail(jwtRequest.getEmail()) == null){
            throw new SecurityException("Provided email is not registered.");
        }
        String jwt=tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
