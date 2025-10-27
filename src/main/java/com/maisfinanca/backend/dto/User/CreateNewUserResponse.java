package com.maisfinanca.backend.dto.User;

public record CreateNewUserResponse(
        String email,
        String username
) { }
