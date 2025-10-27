package com.maisfinanca.backend.dto.User;

public record UpdateUserRequest(
        String email,
        String username,
        String password
) { }
