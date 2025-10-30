package com.maisfinanca.backend.dto.Transaction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTransactionRequest {
    private Long id;
}
