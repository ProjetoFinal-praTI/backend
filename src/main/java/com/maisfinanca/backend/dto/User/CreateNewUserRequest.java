package com.maisfinanca.backend.dto.User;

public record CreateNewUserRequest(
        String email,
        String username,
        String password
) {}