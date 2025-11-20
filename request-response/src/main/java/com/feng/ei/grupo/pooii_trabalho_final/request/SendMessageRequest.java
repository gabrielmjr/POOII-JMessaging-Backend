package com.feng.ei.grupo.pooii_trabalho_final.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendMessageRequest (
        @NotBlank
        @Email
        String toEmail,
        @NotBlank
        String content
) {}
