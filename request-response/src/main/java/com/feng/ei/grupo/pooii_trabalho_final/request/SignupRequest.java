package com.feng.ei.grupo.pooii_trabalho_final.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank
        String username,
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 6)
        String password
) {
}
