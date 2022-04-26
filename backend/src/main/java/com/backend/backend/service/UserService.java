package com.backend.backend.service;

import com.backend.backend.dto.CreateUserRequestDTO;
import com.backend.backend.dto.GetCryptoListResponseDTO;
import com.backend.backend.dto.GetCurrentUserResponseDTO;
import com.backend.backend.mapper.CryptocurrencyMapper;
import com.backend.backend.mapper.UserMapper;
import com.backend.backend.models.User;
import com.backend.backend.models.Wallet;
import com.backend.backend.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
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
        userRepository.save(user);
        return user;
    }

    public GetCurrentUserResponseDTO getCurrentUser(){
        GetCurrentUserResponseDTO user = userMapper.toGetUserDTOEntity(getLoggedInUser());
        return user;
    }

    public List<GetCryptoListResponseDTO> getCryptoList() {
        User user = getLoggedInUser();
        List<GetCryptoListResponseDTO> cryptoList = new ArrayList<GetCryptoListResponseDTO>();
//        user.getCurrencyList().forEach(currency -> {
//            cryptoList.add(cryptocurrencyMapper.toGetCryptoListResponseDTOEntity(currency));
//        });
//        return cryptoList;
        return null;
    }

    public Wallet getWallet(){
//        return getLoggedInUser().getWallet();
        return null;
    }

    public void addMoney(Double value) {
        User user = getLoggedInUser();
        Double newBalance = Double.valueOf(user.getCurrentCardBalance() + value);
        user.setCurrentCardBalance(7000.0);
        userRepository.save(user);
    }

    public void buyCryptocurrency(UUID cryptoId, Double value) {
        User user = getLoggedInUser();
//        Wallet wallet = user.getWallet();
//        wallet.setTotalBalance(wallet.getTotalBalance() - value);
//        wallet.getCryptocurrencies().forEach(crypto -> {
//            if(crypto.getCryptocurrencyId().equals(cryptoId)){
//                crypto.setAmount(crypto.getAmount() - value);
//            }
//        });
    }

    public void sellCryptocurrency(UUID cryptoId, Double value) {
        User user = getLoggedInUser();
//        Wallet wallet = user.getWallet();
//        wallet.setTotalBalance(wallet.getTotalBalance() + value);
//        wallet.getCryptocurrencies().forEach(crypto -> {
//            if(crypto.getCryptocurrencyId().equals(cryptoId)){
//                crypto.setAmount(crypto.getAmount() + value);
//            }
//        });
    }
}
