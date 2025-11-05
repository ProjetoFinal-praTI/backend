package com.maisfinanca.backend.dto.Transactions.Income;

import java.math.BigDecimal;

public record TotalIncomeResponse(
        Long userId,
        BigDecimal totalBalance
) {}
