package com.maisfinanca.backend.entity;

import com.maisfinanca.backend.entity.User; 
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal value;

    @Column(length = 512)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    private String category;

    @Column(nullable = false)
    private String transactionType; 

    @Column(nullable = false)
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
