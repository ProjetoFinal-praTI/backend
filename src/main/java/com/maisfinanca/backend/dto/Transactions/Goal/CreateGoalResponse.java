package com.maisfinanca.backend.dto.Transactions.Goal;

import java.math.BigDecimal;

public record CreateGoalResponse(
        Long goalId,
        String name,
        BigDecimal targetValue,
        BigDecimal currentValue
) {}
