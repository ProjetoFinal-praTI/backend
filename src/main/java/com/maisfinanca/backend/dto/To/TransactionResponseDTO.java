package com.maisfinanca.backend.dto.To;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
    private Long id;
    private Integer value;
    private String description;
    private LocalDate date;
    private String category;
    private String transactionType;
    private String paymentMethod;
}
