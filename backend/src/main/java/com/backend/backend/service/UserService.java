package com.backend.backend.service;

import com.backend.backend.models.Cryptocurrency;
import com.backend.backend.models.User;
import com.backend.backend.dto.CreateUserRequestDTO;
import com.backend.backend.mapper.UserMapper;
import com.backend.backend.repository.UserRepository;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
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
        User user = userMapper.toEntity(userDTO);
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), salt);
        user.setPassword(hashedPassword);
        user.setCurrentCardBalance(Double.valueOf(50000));
        userRepository.save(user);
        return user;
    }

    public List<Cryptocurrency> getCryptoList() {
        User user = getLoggedInUser();
        return user.getCurrencyList();
    }

    public Double getUsersMoney() {
        User user = getLoggedInUser();
        return user.getCurrentCardBalance();
    }

    public void addMoney(Double value) {
        User user = getLoggedInUser();
        user.setCurrentCardBalance(Double.valueOf(user.getCurrentCardBalance() + value));
        userRepository.save(user);
    }

    public void buyCryptocurrency(String cryptoName, Double value) {
        User user = getLoggedInUser();
        user.getCurrencyList().forEach(crypto -> {
            if(crypto.getName().equals(cryptoName)){
                crypto.setValue(crypto.getValue() + value);
            }
        });
        // sa wallet-a total balance ne sa creditne
        user.setCurrentCardBalance(user.getCurrentCardBalance() - value);
    }

    public void sellCryptocurrency(String cryptoName, Double value) {
        User user = getLoggedInUser();
        user.getCurrencyList().forEach(crypto -> {
            if(crypto.getName().equals(cryptoName)){
                crypto.setValue(crypto.getValue() - value);
            }
        });
        user.setCurrentCardBalance(user.getCurrentCardBalance() + value);
    }
}
