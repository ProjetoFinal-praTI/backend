package com.maisfinanca.backend.controller;

import com.maisfinanca.backend.dto.ResponseWrapper;
import com.maisfinanca.backend.dto.Transaction.*;
import com.maisfinanca.backend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
}
