package com.maisfinanca.backend.dto.Transactions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteTransactionResponse {
    private Long id;
    private boolean deleted;
}
