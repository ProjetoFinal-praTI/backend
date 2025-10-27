package com.maisfinanca.backend.service;

import com.maisfinanca.backend.Entity.PasswordResetToken;
import com.maisfinanca.backend.Entity.User;
import com.maisfinanca.backend.exception.BusinessException;
import com.maisfinanca.backend.exception.InvalidInputException;
import com.maisfinanca.backend.exception.NotFoundException;
import com.maisfinanca.backend.repository.PasswordResetTokenRepository;
import com.maisfinanca.backend.repository.UserRepository;
import com.maisfinanca.backend.dto.Reset.PasswordRecoveryRequest;
import com.maisfinanca.backend.dto.Reset.PasswordRecoveryResponse;
import com.maisfinanca.backend.dto.Reset.PasswordResetRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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

        Optional<PasswordResetToken> existingTokenOpt = tokenRepository.findByEmail(user.getEmail())
                .filter(token -> !token.isUsed() && token.getExpirationDate().isAfter(LocalDateTime.now()));

        PasswordResetToken resetToken;
        String tokenValue;

        if (existingTokenOpt.isPresent()) {
            resetToken = existingTokenOpt.get();
            tokenValue = resetToken.getToken();
        } else {
            tokenValue = String.format("%06d", new Random().nextInt(999999));
            LocalDateTime expiration = LocalDateTime.now().plusMinutes(15);

            resetToken = PasswordResetToken.builder()
                    .email(user.getEmail())
                    .token(tokenValue)
                    .expirationDate(expiration)
                    .used(false)
                    .build();

            tokenRepository.save(resetToken);
        }

        sendRecoveryEmail(user.getEmail(), tokenValue);
        return new PasswordRecoveryResponse("Código de recuperação enviado para o e-mail informado.");
    }

    private void sendRecoveryEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recuperação de senha - +Finança");
        message.setText("""
                Seu código de recuperação é: %s
                Válido por 15 minutos.
                """.formatted(token));
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
