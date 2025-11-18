package com.maisfinanca.backend.dto.To;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private BigDecimal value;
    private String description;
    private LocalDate date;
    private String category;
    private String transactionType;
    private String paymentMethod;
    private Long userId;
}
