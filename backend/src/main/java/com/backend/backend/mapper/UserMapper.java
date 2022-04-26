package com.backend.backend.mapper;

import com.backend.backend.dto.GetCurrentUserResponseDTO;
import com.backend.backend.models.User;
import com.backend.backend.dto.CreateUserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User toUserEntity(CreateUserRequestDTO userRequestDTO){
        return User.builder()
                   .firstName(userRequestDTO.getFirstName())
                   .lastName(userRequestDTO.getLastName())
                   .email(userRequestDTO.getEmail())
                   .password(userRequestDTO.getPassword())
                   .build();
    }

    public GetCurrentUserResponseDTO toGetUserDTOEntity(User user){
        return GetCurrentUserResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .currentCardBalance(user.getCurrentCardBalance())
                .build();
    }
}
