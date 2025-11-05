package com.maisfinanca.backend.dto.Transactions.Goal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateGoalRequest(
        @NotNull(message = "O ID do usuário é obrigatório")
        Long userId,

        @NotBlank(message = "O nome da meta é obrigatório")
        String name,

        @NotNull(message = "O valor final da meta é obrigatório")
        @Min(value = 1, message = "O valor final deve ser maior que zero")
        BigDecimal targetValue
) {}
