package com.edugamefy.backend.service;

import com.edugamefy.backend.Entity.User;
import com.edugamefy.backend.exception.NotFoundException;
import com.edugamefy.backend.viewModels.users.CreateNewUserRequest;
import com.edugamefy.backend.viewModels.users.CreateNewUserResponse;
import com.edugamefy.backend.repository.UserRepository;
import com.edugamefy.backend.viewModels.users.UpdateUserRequest;
import com.edugamefy.backend.viewModels.users.UpdateUserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.List;

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

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user)
    {
        this.userRepository.save(user);
    }

    public CreateNewUserResponse createUser(CreateNewUserRequest user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username já cadastrado");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        User newUser = new User(user);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        this.saveUser(newUser);

        return new CreateNewUserResponse(newUser.getEmail(), newUser.getUsername());
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public User findUserById (Long id) {
        return this.userRepository.findUserById(id).orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado"));
    }

    public UpdateUserResponse updateUser(Long id, UpdateUserRequest request) throws Exception {
        User user = findUserById(id);

        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email());
        }
        if (request.username() != null && !request.username().isBlank()) {
            user.setUsername(request.username());
        }
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        User updated = userRepository.save(user);
        return new UpdateUserResponse(updated.getId(), updated.getUsername(), updated.getEmail());
    }

    public void deleteUser(Long id) throws Exception {
        User user = findUserById(id);
        userRepository.delete(user);
    }
}