package com.backend.backend.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class JwtRequest {
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @NotNull
    @Size(min = 4, max = 128)
    private String password;
}
