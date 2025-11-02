package com.maisfinanca.backend.dto.User;

import java.time.LocalDate;

public record UpdateUserResponse(
        Long id,
        String username,
        String email,
        String phone,
        String location,
        LocalDate birthDate
) { }
