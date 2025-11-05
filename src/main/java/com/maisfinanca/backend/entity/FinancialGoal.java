package com.maisfinanca.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "financial_goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal targetValue;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal currentValue;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
