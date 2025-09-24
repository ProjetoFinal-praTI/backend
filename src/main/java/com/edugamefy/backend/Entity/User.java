package com.edugamefy.backend.Entity;

import com.edugamefy.backend.viewModels.users.CreateNewUserRequest;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

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

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private BigDecimal balance;

    public User(CreateNewUserRequest request){
        this.email = request.email();
        this.username = request.username();
        this.password = request.password();
        this.balance = request.balance();
    }
}
