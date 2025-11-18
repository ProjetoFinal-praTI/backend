package com.maisfinanca.backend.entity;

import com.maisfinanca.backend.dto.User.CreateNewUserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotNull(message = "Email cannot be null")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String phone;

    private String location;

    private LocalDate birthDate;

    public User(CreateNewUserRequest request){
        this.email = request.email();
        this.username = request.username();
        this.password = request.password();
    }
}
