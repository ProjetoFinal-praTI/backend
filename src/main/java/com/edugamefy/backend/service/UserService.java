package com.edugamefy.backend.service;

import com.edugamefy.backend.Entity.User;
import com.edugamefy.backend.exception.InvalidInputException;
import com.edugamefy.backend.exception.NotFoundException;
import com.edugamefy.backend.viewModels.users.CreateNewUserRequest;
import com.edugamefy.backend.viewModels.users.CreateNewUserResponse;
import com.edugamefy.backend.viewModels.balance.NewBalanceResponse;
import com.edugamefy.backend.viewModels.balance.NewBalanceRequest;
import com.edugamefy.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User updateUser (User user) {
        return this.userRepository.save(user);
    }
    public void saveUser(User user)
    {
        this.userRepository.save(user);
    }

    public CreateNewUserResponse createUser(CreateNewUserRequest user) {
        User newUser = new User(user);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        this.saveUser(newUser);

        return new CreateNewUserResponse(newUser.getEmail(), newUser.getUsername(), newUser.getBalance());
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public User findUserById (Long id) {
        return this.userRepository.findUserById(id).orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado"));
    }

    public NewBalanceResponse addBalance(Long id, NewBalanceRequest balance) {
        if (balance.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException("O valor para adicionar deve ser maior que zero.");
        }

        User user = findUserById(id);

        if (user.getBalance() == null) {
            user.setBalance(BigDecimal.ZERO);
        }

        user.setBalance(user.getBalance().add(balance.amount()));

        this.saveUser(user);

        return new NewBalanceResponse(user.getBalance());
    }
}
