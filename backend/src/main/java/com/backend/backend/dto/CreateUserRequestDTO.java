package com.backend.backend.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {
    @NotNull
    @Email(message = "email address not valid.")
    @Size(min = 5, max = 50, message = "email length must be between 5 and 50 characters.")
    protected String email;

    @NotNull
    @Size(min = 4, max = 128, message = "password length must be longer than 4 character.")
    protected String password;

    @NotNull
    @Size(min = 1, max = 30, message = "firstName length must be between 1 and 30 characters.")
    protected String firstName;

    @NotNull
    @Size(min = 1, max = 30, message = "lastName length must be between 1 and 30 characters.")
    protected String lastName;
}
