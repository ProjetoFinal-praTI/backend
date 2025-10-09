package com.edugamefy.backend.Entity;

import com.edugamefy.backend.Entity.User; 
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // valor em centavos (ex: R$ 1,00 = 10000)
    @Column(nullable = false)
    private Integer value;

    @Column(length = 512)
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    private String category;

    @Column(nullable = false)
    private String transactionType; // income || expense

    @Column(nullable = false)
    private String paymentMethod; // pix || credit || debit || cash || bank slip

    //relacionamento com User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // cria FK no banco
    private User user;
}
