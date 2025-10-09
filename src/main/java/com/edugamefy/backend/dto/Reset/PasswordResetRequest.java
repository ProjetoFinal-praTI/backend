package com.edugamefy.backend.dto.Reset;

import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequest(
        @NotBlank(message = "Código é obrigatório")
        String token,
        @NotBlank(message = "Nova senha é obrigatória")
        String newPassword
) {}
