package com.maisfinanca.backend.dto.Transactions;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionResponse {
    private Long id;
    private BigDecimal value;
    private String description;
    private LocalDateTime date;
    private String category;
    private String transactionType;
    private String paymentMethod;
    private Long userId;
}
