package com.edugamefy.backend.viewModels.users;

public record UpdateUserRequest(
        String email,
        String username,
        String password
) { }
