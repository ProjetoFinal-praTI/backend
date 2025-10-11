package com.edugamefy.backend.controller;

import com.edugamefy.backend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edugamefy.backend.dto.TransactionDTO;
import java.util.List;
import com.edugamefy.backend.Entity.Transaction;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody TransactionDTO dto) {
        service.save(dto);
        return ResponseEntity.ok("Transação concluída com sucesso!");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findByUserId(userId));
    }
}
