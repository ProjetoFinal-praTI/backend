package com.maisfinanca.backend.Entity;

import com.maisfinanca.backend.Entity.User; 
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false)
    private Integer value;

    @Column(length = 512)
    private String description;

    @Column(nullable = false)
    private LocalDate date;

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
