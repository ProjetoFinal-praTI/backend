package com.edugamefy.backend.service;

import com.edugamefy.backend.dto.TransactionDTO;
import com.edugamefy.backend.Entity.Transaction;
import com.edugamefy.backend.Entity.User;
import com.edugamefy.backend.repository.TransactionRepository;
import com.edugamefy.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction save(TransactionDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Transaction transaction = Transaction.builder()
                .value(dto.getValue())
                .description(dto.getDescription())
                .date(dto.getDate())
                .category(dto.getCategory())
                .transactionType(dto.getTransactionType())
                .paymentMethod(dto.getPaymentMethod())
                .user(user)
                .build();

        return transactionRepository.save(transaction);
    }

    public List<Transaction> findByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
