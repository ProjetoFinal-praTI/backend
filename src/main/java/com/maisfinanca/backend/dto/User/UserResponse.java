package com.maisfinanca.backend.dto.User;

public record UserResponse(
        Long id,
        String username,
        String email
) { }
