package com.edugamefy.backend.service;

import com.edugamefy.backend.Entity.PasswordResetToken;
import com.edugamefy.backend.Entity.User;
import com.edugamefy.backend.dto.Reset.PasswordRecoveryRequest;
import com.edugamefy.backend.dto.Reset.PasswordRecoveryResponse;
import com.edugamefy.backend.dto.Reset.PasswordResetRequest;
import com.edugamefy.backend.exception.BusinessException;
import com.edugamefy.backend.exception.InvalidInputException;
import com.edugamefy.backend.exception.NotFoundException;
import com.edugamefy.backend.repository.PasswordResetTokenRepository;
import com.edugamefy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public PasswordRecoveryResponse requestPasswordReset(PasswordRecoveryRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("E-mail não encontrado"));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .email(user.getEmail())
                .token(token)
                .expirationDate(expiration)
                .used(false)
                .build();

        tokenRepository.save(resetToken);

        sendRecoveryEmail(user.getEmail(), token);

        return new PasswordRecoveryResponse("Código de recuperação enviado para o e-mail informado.");
    }

    private void sendRecoveryEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recuperação de senha - EduGamefy");
        message.setText("Seu código de recuperação é: " + token + "\n\nVálido por 15 minutos.");
        mailSender.send(message);
    }

    public PasswordRecoveryResponse resetPassword(PasswordResetRequest request) {
        PasswordResetToken token = tokenRepository.findByToken(request.token())
                .orElseThrow(() -> new InvalidInputException("Código inválido"));

        if (token.isUsed() || token.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Código expirado ou já utilizado.");
        }

        User user = userRepository.findByEmail(token.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);

        return new PasswordRecoveryResponse("Senha redefinida com sucesso!");
    }
}
