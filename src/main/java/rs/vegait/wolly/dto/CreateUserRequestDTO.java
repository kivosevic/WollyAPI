package rs.vegait.wolly.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.vegait.wolly.validation.UniqueEmail;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {
    @NotEmpty
    @Email(message = "email address not valid.")
    @Size(min = 5, max = 50, message = "email length must be between 5 and 50 characters.")
    @UniqueEmail
    protected String email;

    @NotEmpty
    @Size(min = 4, max = 128, message = "password length must be longer than 4 character.")
    protected String password;

    @NotEmpty
    @Size(min = 1, max = 30, message = "firstName length must be between 1 and 30 characters.")
    protected String firstName;

    @NotEmpty
    @Size(min = 1, max = 30, message = "lastName length must be between 1 and 30 characters.")
    protected String lastName;
}
