package com.edugamefy.backend.viewModels.users;

import java.math.BigDecimal;

public record UserResponse(
        Long id,
        String username,
        String email
) { }
