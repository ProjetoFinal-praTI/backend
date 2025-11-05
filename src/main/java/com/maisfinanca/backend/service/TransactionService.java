package com.maisfinanca.backend.service;

import com.maisfinanca.backend.dto.Transactions.*;
import com.maisfinanca.backend.dto.Transactions.Goal.*;
import com.maisfinanca.backend.dto.Transactions.Income.*;
import com.maisfinanca.backend.entity.FinancialGoal;
import com.maisfinanca.backend.entity.Transaction;
import com.maisfinanca.backend.entity.User;
import com.maisfinanca.backend.exception.InvalidInputException;
import com.maisfinanca.backend.exception.NotFoundException;
import com.maisfinanca.backend.repository.FinancialGoalRepository;
import com.maisfinanca.backend.repository.TransactionRepository;
import com.maisfinanca.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private final FinancialGoalRepository goalRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
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

    public List<GetTransactionResponse> getTransactionsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        List<Transaction> transactions = transactionRepository.findByUserOrderByDateDesc(user);

        return transactions.stream()
                .map(t -> new GetTransactionResponse(
                        t.getId(),
                        t.getValue(),
                        t.getDescription(),
                        t.getDate(),
                        t.getCategory(),
                        t.getTransactionType(),
                        t.getPaymentMethod(),
                        t.getUser().getId()
                ))
                .toList();
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

    @Transactional
    public AddIncomeResponse addIncome(AddIncomeRequest request) {

        if (request.value() == null || request.value().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException("O valor para adicionar deve ser maior que zero.");
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        String paymentMethod = request.paymentMethod() != null ? request.paymentMethod() : "NÃO INFORMADO";

        Transaction transaction = Transaction.builder()
                .user(user)
                .value(request.value())
                .description(request.description())
                .transactionType("INCOME")
                .paymentMethod(paymentMethod)
                .date(LocalDateTime.now())
                .build();

         transactionRepository.save(transaction);

        BigDecimal total = transactionRepository.getTotalIncomeByUser(user);
        return new AddIncomeResponse(user.getId(), total);
    }

    @Transactional
    public RemoveIncomeResponse removeIncome(RemoveIncomeRequest request) {

        if (request.value() == null || request.value().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException("O valor para remover deve ser maior que zero.");
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        BigDecimal currentTotal = transactionRepository.getTotalIncomeByUser(user);

        if (currentTotal.compareTo(request.value()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para remover esse valor.");
        }

        String paymentMethod = request.paymentMethod() != null ? request.paymentMethod() : "NÃO INFORMADO";

        Transaction transaction = Transaction.builder()
                .user(user)
                .value(request.value().negate())
                .description(request.description())
                .transactionType("INCOME")
                .paymentMethod(paymentMethod)
                .date(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        BigDecimal newTotal = transactionRepository.getTotalIncomeByUser(user);
        return new RemoveIncomeResponse(user.getId(), newTotal);
    }

    public TotalIncomeResponse getTotalIncome(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        BigDecimal total = transactionRepository.getTotalIncomeByUser(user);
        return new TotalIncomeResponse(user.getId(), total);
    }

    @Transactional
    public CreateGoalResponse createGoal(CreateGoalRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        FinancialGoal goal = FinancialGoal.builder()
                .name(request.name())
                .targetValue(request.targetValue())
                .currentValue(BigDecimal.ZERO)
                .user(user)
                .build();

        goalRepository.save(goal);

        return new CreateGoalResponse(goal.getId(), goal.getName(), goal.getTargetValue(), goal.getCurrentValue());
    }

    public List<GoalResponse> getGoalsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return goalRepository.findByUser(user).stream()
                .map(g -> new GoalResponse(
                        g.getId(),
                        g.getName(),
                        g.getTargetValue(),
                        g.getCurrentValue(),
                        g.getCurrentValue().compareTo(g.getTargetValue()) >= 0
                ))
                .toList();
    }

    @Transactional
    public UpdateGoalProgressResponse updateGoalProgress(Long goalId, UpdateGoalProgressRequest request) {

        if (request.newCurrentValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor adicionado não pode ser negativo.");
        }

        FinancialGoal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new NotFoundException("Meta não encontrada"));

        BigDecimal currentValue = goal.getCurrentValue() != null ? goal.getCurrentValue() : BigDecimal.ZERO;

        BigDecimal updatedValue = currentValue.add(request.newCurrentValue());

        if (updatedValue.compareTo(goal.getTargetValue()) > 0) {
            throw new IllegalArgumentException("O valor atual não pode ultrapassar o valor final definido.");
        }

        goal.setCurrentValue(updatedValue);
        goalRepository.save(goal);

        boolean reached = goal.getCurrentValue().compareTo(goal.getTargetValue()) >= 0;
        return new UpdateGoalProgressResponse(goal.getId(), updatedValue, reached);
    }

    @Transactional
    public void deleteGoal(Long goalId) {
        FinancialGoal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new NotFoundException("Meta não encontrada"));
        goalRepository.delete(goal);
    }
}
