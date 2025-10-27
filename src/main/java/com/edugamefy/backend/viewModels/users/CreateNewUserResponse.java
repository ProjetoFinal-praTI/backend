package com.edugamefy.backend.viewModels.users;

import java.math.BigDecimal;

public record CreateNewUserResponse(
        String email,
        String username
) { }
