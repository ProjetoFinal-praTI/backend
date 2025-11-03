package com.maisfinanca.backend.dto.Transactions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteTransactionRequest {
    private Long id;
}
