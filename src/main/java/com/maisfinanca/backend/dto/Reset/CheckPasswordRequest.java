package com.maisfinanca.backend.dto.Reset;

public record CheckPasswordRequest(
        String email,
        String rawPassword
) {}
