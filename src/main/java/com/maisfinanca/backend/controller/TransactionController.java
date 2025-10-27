package com.maisfinanca.backend.controller;

import com.maisfinanca.backend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.maisfinanca.backend.dto.TransactionDTO;
import com.maisfinanca.backend.dto.TransactionResponseDTO;

import java.util.List;

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
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUser(@PathVariable Long userId) {
        List<TransactionResponseDTO> transactions = service.findByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
