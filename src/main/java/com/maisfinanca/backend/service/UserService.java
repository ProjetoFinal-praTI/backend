package com.maisfinanca.backend.service;

import com.maisfinanca.backend.Entity.User;
import com.maisfinanca.backend.exception.NotFoundException;
import com.maisfinanca.backend.dto.User.CreateNewUserRequest;
import com.maisfinanca.backend.dto.User.CreateNewUserResponse;
import com.maisfinanca.backend.repository.UserRepository;
import com.maisfinanca.backend.dto.User.UpdateUserRequest;
import com.maisfinanca.backend.dto.User.UpdateUserResponse;
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
        if (userRepository.findByUsername(user.username()).isPresent()) {
            throw new IllegalArgumentException("Username já cadastrado");
        }

        if (userRepository.findByEmail(user.email()).isPresent()) {
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