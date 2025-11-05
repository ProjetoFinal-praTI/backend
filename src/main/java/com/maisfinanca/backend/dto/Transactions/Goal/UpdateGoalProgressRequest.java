package com.maisfinanca.backend.dto.Transactions.Goal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateGoalProgressRequest(
        @NotNull(message = "O novo valor atual é obrigatório")
        @Min(value = 0, message = "O valor atual deve ser positivo")
        BigDecimal newCurrentValue
) {}
