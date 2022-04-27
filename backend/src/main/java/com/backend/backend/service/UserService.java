package com.backend.backend.service;

import com.backend.backend.dto.CreateUserRequestDTO;
import com.backend.backend.dto.GetCryptoListResponseDTO;
import com.backend.backend.dto.GetCurrentUserResponseDTO;
import com.backend.backend.dto.GetWalletResponseDTO;
import com.backend.backend.mapper.CryptocurrencyMapper;
import com.backend.backend.mapper.UserMapper;
import com.backend.backend.mapper.WalletMapper;
import com.backend.backend.models.User;
import com.backend.backend.models.Wallet;
import com.backend.backend.repository.UserRepository;
import com.backend.backend.repository.WalletRepository;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final UserMapper userMapper;
    private final WalletMapper walletMapper;
    private final CryptocurrencyMapper cryptocurrencyMapper;

    private User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new EntityNotFoundException("You must log in first");
        }
        return findByEmail(authentication.getName());
    }

    public User findByEmail(@NotNull String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Could not find user with provided email."));
    }

    public User create(CreateUserRequestDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EntityExistsException("This email is already in use.");
        }
        User user = userMapper.toUserEntity(userDTO);
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), salt);
        user.setPassword(hashedPassword);
        user.setCurrentCardBalance(Double.valueOf(50000));
        user.setRole("USER");
        //user.setId(UUID.randomUUID().toString());

        /*Cryptocurrency cryptocurrency = new Cryptocurrency(UUID.randomUUID(), "Bitcoin", "BTC", "~/images/btc.png", 12345.6);
        cryptocurrency*/

        Wallet wallet = new Wallet();
        //wallet.setId(UUID.randomUUID());
        userRepository.save(user);
        walletRepository.save(wallet);

        wallet.setUser(user);
        user.setWallet(wallet);

        userRepository.save(user);
        walletRepository.save(wallet);
        return user;
    }

    public GetCurrentUserResponseDTO getCurrentUser(){
        GetCurrentUserResponseDTO user = userMapper.toGetUserResponseDTOEntity(getLoggedInUser());
        return user;
    }

    public List<GetCryptoListResponseDTO> getCryptoList() {
        User user = getLoggedInUser();
//        List<GetCryptoListResponseDTO> cryptoList = new ArrayList<GetCryptoListResponseDTO>();
//        user.getCurrencyList().forEach(currency -> {
//            cryptoList.add(cryptocurrencyMapper.toGetCryptoListResponseDTOEntity(currency));
//        });
//        user.getCurrencyList().stream().map(CryptocurrencyMapper::toGetCryptoListResponseDTOEntity).collect(Collectors.toList());

        return user.getCurrencyList().stream().map(CryptocurrencyMapper::toGetCryptoListResponseDTOEntity).collect(Collectors.toList());
    }

    public GetWalletResponseDTO getWallet(){
        Wallet wallet = walletRepository.getById(getLoggedInUser().getWallet().getId());
        return walletMapper.toGetWalletResponseDTOEntity(wallet);
    }

    public void addMoney(Double value) {
        User user = getLoggedInUser();
        Double newBalance = user.getCurrentCardBalance() + value;
        user.setCurrentCardBalance(newBalance);
    }

    public void buyCryptocurrency(UUID cryptoId, Double value) {
        User user = getLoggedInUser();
        Wallet wallet = walletRepository.getById(getLoggedInUser().getWallet().getId());
        wallet.setTotalBalance(wallet.getTotalBalance() - value);
        wallet.getCryptocurrencies().forEach(crypto -> {
            if(crypto.getCryptocurrencyId().equals(cryptoId)){
                crypto.setAmount(crypto.getAmount() - value);
            }
        });
    }

    public void sellCryptocurrency(UUID cryptoId, Double value) {
        User user = getLoggedInUser();
        Wallet wallet = walletRepository.getById(getLoggedInUser().getWallet().getId());
        wallet.setTotalBalance(wallet.getTotalBalance() + value);
        wallet.getCryptocurrencies().forEach(crypto -> {
            if(crypto.getCryptocurrencyId().equals(cryptoId)){
                crypto.setAmount(crypto.getAmount() + value);
            }
        });
    }
}
