package com.maisfinanca.backend.dto.Transactions.Goal;

import java.math.BigDecimal;

public record UpdateGoalProgressResponse(
        Long goalId,
        BigDecimal updatedValue,
        Boolean goalReached
) {}
