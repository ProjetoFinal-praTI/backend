package com.maisfinanca.backend.controller;

import com.maisfinanca.backend.security.SecurityConfig;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Actuator")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class ActuatorHealth {

    @GetMapping("/actuator/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("message", "System ON"));
    }
}
