package com.edugamefy.backend.dto.Reset;

public record CheckPasswordRequest(
        String email,
        String rawPassword
) {}
