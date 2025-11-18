package com.maisfinanca.backend.repository;

import com.maisfinanca.backend.entity.FinancialGoal;
import com.maisfinanca.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Long> {
    List<FinancialGoal> findByUser(User user);
}
