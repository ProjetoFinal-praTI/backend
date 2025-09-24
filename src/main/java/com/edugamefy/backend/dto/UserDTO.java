package com.edugamefy.backend.dto;

import java.math.BigDecimal;
public record UserDTO (String email, String password, String username, BigDecimal balance){
}