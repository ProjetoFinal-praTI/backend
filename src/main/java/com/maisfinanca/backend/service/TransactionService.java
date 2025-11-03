package com.maisfinanca.backend.service;

import com.maisfinanca.backend.dto.Transactions.*;
import com.maisfinanca.backend.entity.Transaction;
import com.maisfinanca.backend.entity.User;
import com.maisfinanca.backend.repository.TransactionRepository;
import com.maisfinanca.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = Transaction.builder()
                .value(request.getValue())
                .description(request.getDescription())
                .date(request.getDate())
                .category(request.getCategory())
                .transactionType(request.getTransactionType())
                .paymentMethod(request.getPaymentMethod())
                .user(user)
                .build();

        transactionRepository.save(transaction);

        return CreateTransactionResponse.builder()
                .id(transaction.getId())
                .value(transaction.getValue())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .category(transaction.getCategory())
                .transactionType(transaction.getTransactionType())
                .paymentMethod(transaction.getPaymentMethod())
                .userId(transaction.getUser().getId())
                .build();
    }

    public GetTransactionResponse getTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        return GetTransactionResponse.builder()
                .id(transaction.getId())
                .value(transaction.getValue())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .category(transaction.getCategory())
                .transactionType(transaction.getTransactionType())
                .paymentMethod(transaction.getPaymentMethod())
                .userId(transaction.getUser().getId())
                .build();
    }

    public UpdateTransactionResponse updateTransaction(UpdateTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        transaction.setValue(request.getValue());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setCategory(request.getCategory());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setPaymentMethod(request.getPaymentMethod());
        transaction.setUser(user);

        transactionRepository.save(transaction);

        return UpdateTransactionResponse.builder()
                .id(transaction.getId())
                .value(transaction.getValue())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .category(transaction.getCategory())
                .transactionType(transaction.getTransactionType())
                .paymentMethod(transaction.getPaymentMethod())
                .userId(transaction.getUser().getId())
                .build();
    }

    public DeleteTransactionResponse deleteTransaction(Long id) {
        boolean exists = transactionRepository.existsById(id);
        if (!exists) throw new RuntimeException("Transaction not found");

        transactionRepository.deleteById(id);

        return DeleteTransactionResponse.builder()
                .id(id)
                .deleted(true)
                .build();
    }
}
