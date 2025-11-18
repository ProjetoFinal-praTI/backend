package com.maisfinanca.backend.dto.Transactions.Goal;

import java.math.BigDecimal;

public record GoalResponse(
        Long id,
        String name,
        BigDecimal targetValue,
        BigDecimal currentValue,
        Boolean achieved
) {}
