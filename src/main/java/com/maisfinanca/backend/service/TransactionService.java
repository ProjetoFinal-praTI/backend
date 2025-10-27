package com.maisfinanca.backend.service;

import com.maisfinanca.backend.dto.TransactionDTO;
import com.maisfinanca.backend.dto.TransactionResponseDTO;
import com.maisfinanca.backend.Entity.Transaction;
import com.maisfinanca.backend.Entity.User;
import com.maisfinanca.backend.repository.TransactionRepository;
import com.maisfinanca.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<TransactionResponseDTO> findByUserId(Long userId) {
        return transactionRepository.findByUserId(userId).stream()
                .map(t -> TransactionResponseDTO.builder()
                        .id(t.getId())
                        .value(t.getValue())
                        .description(t.getDescription())
                        .date(t.getDate())
                        .category(t.getCategory())
                        .transactionType(t.getTransactionType())
                        .paymentMethod(t.getPaymentMethod())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada");
        }
        transactionRepository.deleteById(id);
    }
}
