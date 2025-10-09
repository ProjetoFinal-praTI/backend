package com.edugamefy.backend.controller;

import com.edugamefy.backend.Entity.User;
import com.edugamefy.backend.dto.Reset.CheckPasswordRequest;
import com.edugamefy.backend.dto.Reset.PasswordRecoveryRequest;
import com.edugamefy.backend.dto.Reset.PasswordRecoveryResponse;
import com.edugamefy.backend.dto.Reset.PasswordResetRequest;
import com.edugamefy.backend.exception.NotFoundException;
import com.edugamefy.backend.repository.UserRepository;
import com.edugamefy.backend.service.PasswordRecoveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/request")
    public ResponseEntity<PasswordRecoveryResponse> requestPasswordReset(
            @RequestBody @Valid PasswordRecoveryRequest request) {
        return ResponseEntity.ok(passwordRecoveryService.requestPasswordReset(request));
    }

    @PostMapping("/reset")
    public ResponseEntity<PasswordRecoveryResponse> resetPassword(
            @RequestBody @Valid PasswordResetRequest request) {
        return ResponseEntity.ok(passwordRecoveryService.resetPassword(request));
    }

    @Profile("dev")
    @PostMapping("/debug/check-password")
    public ResponseEntity<?> checkPassword(@RequestBody CheckPasswordRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        boolean matches = passwordEncoder.matches(req.rawPassword(), user.getPassword());

        return ResponseEntity.ok(Map.of("matches", matches));
    }

}
