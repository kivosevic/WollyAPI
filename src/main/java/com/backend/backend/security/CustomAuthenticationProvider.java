package com.backend.backend.security;

import com.backend.backend.models.User;
import com.backend.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByEmail((String) authentication.getPrincipal());
        return optionalUser.map(user -> {
                    String plainPassword = (String) authentication.getCredentials();
                    if (!BCrypt.checkpw(plainPassword.trim(), user.getPassword())) {
                        throw new SecurityException("User can not be authenticated.");
                    }

                    return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                            authentication.getCredentials(),
                            Collections.emptyList());
                })
                .orElseThrow(() -> new SecurityException("User can not be authenticated"));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}