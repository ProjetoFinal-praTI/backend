package com.maisfinanca.backend.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserRequest(
        @Email(message = "E-mail inválido")
        @Size(max = 120, message = "O e-mail deve ter no máximo 120 caracteres")
        String email,
        @Size(min = 2, max = 80, message = "O nome deve ter entre 2 e 80 caracteres")
        String username,
        String password,
        @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "Telefone deve estar no formato (99) 99999-9999")
        String phone,
        String location,
        @Past(message = "A data de nascimento deve ser uma data no passado")
        LocalDate birthDate
) { }
