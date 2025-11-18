package com.maisfinanca.backend.repository;

import com.maisfinanca.backend.entity.Transaction;
import com.maisfinanca.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(t.value), 0) FROM Transaction t WHERE t.user = :user AND t.transactionType = 'INCOME'")
    BigDecimal getTotalIncomeByUser(User user);

    List<Transaction> findByUserOrderByDateDesc(User user);
}
