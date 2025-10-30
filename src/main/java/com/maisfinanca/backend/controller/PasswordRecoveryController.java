package com.maisfinanca.backend.controller;

import com.maisfinanca.backend.entity.User;
import com.maisfinanca.backend.exception.NotFoundException;
import com.maisfinanca.backend.repository.UserRepository;
import com.maisfinanca.backend.service.PasswordRecoveryService;
import com.maisfinanca.backend.dto.Reset.CheckPasswordRequest;
import com.maisfinanca.backend.dto.Reset.PasswordRecoveryRequest;
import com.maisfinanca.backend.dto.Reset.PasswordRecoveryResponse;
import com.maisfinanca.backend.dto.Reset.PasswordResetRequest;

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
