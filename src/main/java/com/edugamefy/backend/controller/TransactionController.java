package com.edugamefy.backend.controller;

import com.edugamefy.backend.Entity.Transaction;
import com.edugamefy.backend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edugamefy.backend.dto.TransactionDTO;


import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    // Criar transação
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO dto) {
    return ResponseEntity.ok(service.save(dto));
}


    // Buscar transações de um usuário
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findByUserId(userId));
    }
}