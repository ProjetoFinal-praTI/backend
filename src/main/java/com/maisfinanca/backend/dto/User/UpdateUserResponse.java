package com.maisfinanca.backend.dto.User;

public record UpdateUserResponse(
        Long id,
        String username,
        String email
) { }
