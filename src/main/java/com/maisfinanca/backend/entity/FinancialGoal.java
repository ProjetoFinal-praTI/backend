package com.maisfinanca.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private LocalDateTime startDate;

    private LocalDateTime finalDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
