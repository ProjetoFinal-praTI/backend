package com.edugamefy.backend.viewModels.users;

import java.math.BigDecimal;

public record UpdateUserResponse(
        Long id,
        String username,
        String email
) { }
