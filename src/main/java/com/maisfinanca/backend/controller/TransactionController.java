package com.maisfinanca.backend.controller;

import com.maisfinanca.backend.dto.ResponseWrapper;
import com.maisfinanca.backend.dto.Transactions.*;
import com.maisfinanca.backend.dto.Transactions.Goal.*;
import com.maisfinanca.backend.dto.Transactions.Income.*;
import com.maisfinanca.backend.security.SecurityConfig;
import com.maisfinanca.backend.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Transaction")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<?>> createTransaction(@RequestBody CreateTransactionRequest request) {
        try {
            CreateTransactionResponse response = transactionService.createTransaction(request);
            ResponseWrapper<CreateTransactionResponse> wrapper = new ResponseWrapper<>(response, true);
            return ResponseEntity.ok(wrapper);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<String> wrapper = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(wrapper);
        } catch (Exception e) {
            ResponseWrapper<String> wrapper = new ResponseWrapper<>("Erro ao criar transação", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wrapper);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<?>> getTransaction(@PathVariable Long id) {
        try {
            GetTransactionResponse response = transactionService.getTransaction(id);
            ResponseWrapper<GetTransactionResponse> wrapper = new ResponseWrapper<>(response, true);
            return ResponseEntity.ok(wrapper);
        } catch (Exception e) {
            ResponseWrapper<String> wrapper = new ResponseWrapper<>("Transação não encontrada", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(wrapper);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper<?>> getTransactionsByUserId(@PathVariable Long userId) {
        try {
            List<GetTransactionResponse> transactions = transactionService.getTransactionsByUser(userId);
            ResponseWrapper<List<GetTransactionResponse>> wrapper = new ResponseWrapper<>(transactions, true);
            return ResponseEntity.ok(wrapper);
        } catch (Exception e) {
            ResponseWrapper<String> wrapper = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(wrapper);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<?>> updateTransaction(
            @PathVariable Long id,
            @RequestBody UpdateTransactionRequest request) {
        try {
            request.setId(id);
            UpdateTransactionResponse response = transactionService.updateTransaction(request);
            ResponseWrapper<UpdateTransactionResponse> wrapper = new ResponseWrapper<>(response, true);
            return ResponseEntity.ok(wrapper);
        } catch (Exception e) {
            ResponseWrapper<String> wrapper = new ResponseWrapper<>("Erro ao atualizar transação", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(wrapper);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<?>> deleteTransaction(@PathVariable Long id) {
        try {
            DeleteTransactionResponse response = transactionService.deleteTransaction(id);
            ResponseWrapper<DeleteTransactionResponse> wrapper = new ResponseWrapper<>(response, true);
            return ResponseEntity.ok(wrapper);
        } catch (Exception e) {
            ResponseWrapper<String> wrapper = new ResponseWrapper<>("Erro ao deletar transação", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(wrapper);
        }
    }

    @PostMapping("/income/add")
    public ResponseEntity<ResponseWrapper<?>> addIncome(@RequestBody @Valid AddIncomeRequest request) {
        try {
            AddIncomeResponse responseData = transactionService.addIncome(request);
            ResponseWrapper<AddIncomeResponse> response = new ResponseWrapper<>(responseData, true);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<String> response = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/income/remove")
    public ResponseEntity<ResponseWrapper<?>> removeIncome(@RequestBody @Valid RemoveIncomeRequest request) {
        try {
            RemoveIncomeResponse responseData = transactionService.removeIncome(request);
            ResponseWrapper<RemoveIncomeResponse> response = new ResponseWrapper<>(responseData, true);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<String> response = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/income/total/{userId}")
    public ResponseEntity<ResponseWrapper<?>> getTotalIncome(@PathVariable Long userId) {
        try {
            TotalIncomeResponse total = transactionService.getTotalIncome(userId);
            ResponseWrapper<TotalIncomeResponse> response = new ResponseWrapper<>(total, true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<String> response = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/goals")
    public ResponseEntity<ResponseWrapper<?>> createGoal(@Valid @RequestBody CreateGoalRequest request) {
        try {
            CreateGoalResponse created = transactionService.createGoal(request);
            ResponseWrapper<CreateGoalResponse> response = new ResponseWrapper<>(created, true);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<String> response = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/goals/{userId}")
    public ResponseEntity<List<GoalResponse>> getGoalsByUser(@PathVariable Long userId) {
        List<GoalResponse> goals = transactionService.getGoalsByUser(userId);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }

    @PutMapping("/goals/{goalId}")
    public ResponseEntity<ResponseWrapper<?>> updateGoalProgress(
            @PathVariable Long goalId,
            @Valid @RequestBody UpdateGoalProgressRequest request
    ) {
        try {
            UpdateGoalProgressResponse updated = transactionService.updateGoalProgress(goalId, request);
            ResponseWrapper<UpdateGoalProgressResponse> response = new ResponseWrapper<>(updated, true);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<String> response = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/goals/{goalId}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId) {
        try {
            transactionService.deleteGoal(goalId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            ResponseWrapper<String> response = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
