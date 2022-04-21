package com.backend.backend.mapper;

import com.backend.backend.domain.User;
import com.backend.backend.dto.CreateUserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User toEntity(CreateUserRequestDTO userRequestDTO){
        return User.builder()
                   .firstName(userRequestDTO.getFirstName())
                   .lastName(userRequestDTO.getLastName())
                   .email(userRequestDTO.getEmail())
                   .password(userRequestDTO.getPassword())
                   .build();
    }
}
