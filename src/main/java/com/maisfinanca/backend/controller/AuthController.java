package com.maisfinanca.backend.controller;

import com.maisfinanca.backend.entity.User;
import com.maisfinanca.backend.config.JwtUtil;
import com.maisfinanca.backend.dto.Login.LoginRequest;
import com.maisfinanca.backend.dto.Login.LoginResponse;
import com.maisfinanca.backend.dto.ResponseWrapper;
import com.maisfinanca.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<?>> login(@RequestBody LoginRequest request) {

        String INVALID_USER_LOGIN = "Nome de usuário ou senha incorretos";

        User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException(INVALID_USER_LOGIN));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            ResponseWrapper<String> respFail = new ResponseWrapper<>(INVALID_USER_LOGIN, false);
            return ResponseEntity.status(401).body(respFail);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("id", user.getId());

        String token = jwtUtil.generateToken(claims, user.getUsername());
        ResponseWrapper<LoginResponse> respSuccess = new ResponseWrapper<>(new LoginResponse(token), true);
        return ResponseEntity.ok(respSuccess);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Logout é feito no client (frontend remove o token).
        // Opcional: implementar blacklist aqui.
        return ResponseEntity.ok("Logout realizado com sucesso (invalide o token no cliente).");
    }
}