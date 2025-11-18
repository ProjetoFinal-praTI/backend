package com.maisfinanca.backend.dto.Transactions.Income;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RemoveIncomeRequest(
        @NotNull(message = "O ID do usuário é obrigatório")
        Long userId,

        @NotNull(message = "O valor é obrigatório")
        @Min(value = 0, message = "O valor deve ser positivo")
        BigDecimal value,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String description,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String paymentMethod
) {}
