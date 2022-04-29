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
import com.backend.backend.models.WalletItem;
import com.backend.backend.repository.CryptocurrencyRepository;
import com.backend.backend.repository.UserRepository;
import com.backend.backend.repository.WalletItemRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final WalletItemRepository walletItemRepository;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final UserMapper userMapper;
    private final WalletMapper walletMapper;
    private final CryptocurrencyMapper cryptocurrencyMapper;

    private User getLoggedInUser() {
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

        Wallet wallet = new Wallet();
        user = userRepository.save(user);
        wallet = walletRepository.save(wallet);
        wallet.setUser(user);
        user.setWallet(wallet);

        user = userRepository.save(user);
        walletRepository.save(wallet);

        return user;
    }

    public GetCurrentUserResponseDTO getCurrentUser() {
        GetCurrentUserResponseDTO user = userMapper.toGetUserResponseDTOEntity(getLoggedInUser());
        return user;
    }

    public List<GetCryptoListResponseDTO> getCryptoList() {
        User user = getLoggedInUser();
        List<GetCryptoListResponseDTO> cryptoList = new ArrayList<>();
        user.getWallet().getWalletItems().forEach(walletItem -> {
            cryptoList.add(CryptocurrencyMapper.toGetCryptoListResponseDTOEntity(cryptocurrencyRepository.getById(walletItem.getCryptocurrencyId())));
        });

        return cryptoList;
    }

    public GetWalletResponseDTO getWallet() {
        Wallet wallet = walletRepository.getById(getLoggedInUser().getWallet().getId());
        return walletMapper.toGetWalletResponseDTOEntity(wallet);
    }

    public void addMoney(Double value) {
        User user = getLoggedInUser();
        Double newBalance = user.getCurrentCardBalance() + value;
        user.setCurrentCardBalance(newBalance);
    }

    public void buyCryptocurrency(UUID cryptoId, Double value) {
        Wallet wallet = walletRepository.getById(getLoggedInUser().getWallet().getId());
        wallet.setTotalBalance(wallet.getTotalBalance() + value);

        AtomicReference<Boolean> valid = new AtomicReference<>(false);
        for (WalletItem walletItem : wallet.getWalletItems()) {
            if (walletItem.getCryptocurrencyId().equals(cryptoId)) {
                walletItem.setAmount(walletItem.getAmount() + value);
                valid.set(true);
            }
        }

        if (!valid.get()) {
            walletItemRepository.save(new WalletItem(cryptoId, value, wallet));
        }
    }

    public void sellCryptocurrency(UUID cryptoId, Double value) {
        Wallet wallet = walletRepository.getById(getLoggedInUser().getWallet().getId());
        wallet.setTotalBalance(wallet.getTotalBalance() - value);
        wallet.getWalletItems().forEach(walletItem -> {
            if (walletItem.getCryptocurrencyId().equals(cryptoId)) {
                walletItem.setAmount(walletItem.getAmount() - value);
            }
        });
    }
}
