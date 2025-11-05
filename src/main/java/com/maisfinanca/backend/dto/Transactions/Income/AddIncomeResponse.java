package com.maisfinanca.backend.dto.Transactions.Income;

import java.math.BigDecimal;

public record AddIncomeResponse(
        Long userId,
        BigDecimal newTotalBalance
) {}
