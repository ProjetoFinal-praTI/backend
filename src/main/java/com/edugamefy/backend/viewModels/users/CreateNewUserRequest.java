package com.edugamefy.backend.viewModels.users;

import java.math.BigDecimal;

public record CreateNewUserRequest(
        String email,
        String username,
        String password
) {}