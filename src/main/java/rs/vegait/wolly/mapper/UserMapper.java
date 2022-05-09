package rs.vegait.wolly.mapper;

import rs.vegait.wolly.dto.CreateUserRequestDTO;
import rs.vegait.wolly.dto.GetCurrentUserResponseDTO;
import rs.vegait.wolly.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User toUserEntity(CreateUserRequestDTO userRequestDTO) {
        return User.builder()
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();
    }

    public GetCurrentUserResponseDTO toGetUserResponseDTOEntity(User user) {
        return GetCurrentUserResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .currentCardBalance(user.getCurrentCardBalance())
                .build();
    }
}
