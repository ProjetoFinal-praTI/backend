package com.maisfinanca.backend.Tests.Service;

import com.maisfinanca.backend.entity.User;
import com.maisfinanca.backend.dto.User.UpdateUserRequest;
import com.maisfinanca.backend.dto.User.UpdateUserResponse;
import com.maisfinanca.backend.exception.NotFoundException;
import com.maisfinanca.backend.repository.UserRepository;
import com.maisfinanca.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User existingUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        existingUser = new User(
                1L,
                "john@example.com",
                "John",
                "encoded123",
                "(11) 99999-8888",
                "São Paulo, SP",
                LocalDate.of(1990, 1, 1)
        );
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest(
                "new@example.com",
                "NewName",
                "123",
                "(11) 98888-7777",
                "Campinas, SP",
                LocalDate.of(1995, 8, 15)
        );

        when(userRepository.findUserById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateUserResponse result = userService.updateUser(1L, request);

        assertEquals("NewName", result.username());
        assertEquals("new@example.com", result.email());
        assertEquals("(11) 98888-7777", existingUser.getPhone());
        assertEquals("Campinas, SP", existingUser.getLocation());
        assertEquals(LocalDate.of(1995, 8, 15), existingUser.getBirthDate());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserNotFound() {
        when(userRepository.findUserById(99L)).thenReturn(Optional.empty());

        UpdateUserRequest request = new UpdateUserRequest(
                "teste@email.com",
                "Teste",
                "123",
                "(11) 98888-7777",
                "Local",
                LocalDate.of(1990, 1, 1)
        );

        NotFoundException e = assertThrows(NotFoundException.class, () ->
                userService.updateUser(99L, request)
        );

        assertEquals("Usuário com ID 99 não encontrado", e.getMessage());
    }

    @Test
    void shouldNotChangeFieldsWhenBlank() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest(
                "",
                "",
                "",
                "",
                "",
                null
        );

        when(userRepository.findUserById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateUserResponse result = userService.updateUser(1L, request);

        // Nenhuma alteração deve ter ocorrido
        assertEquals("John", result.username());
        assertEquals("john@example.com", result.email());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void shouldThrowExceptionWhenBirthDateIsFuture() {
        UpdateUserRequest request = new UpdateUserRequest(
                "future@email.com",
                "FutureUser",
                "123",
                "(11) 98888-7777",
                "Campinas, SP",
                LocalDate.now().plusDays(10) // data futura
        );

        when(userRepository.findUserById(1L)).thenReturn(Optional.of(existingUser));

        assertThrows(IllegalArgumentException.class, () ->
                userService.updateUser(1L, request)
        );
    }

    @Test
    void shouldNotChangePasswordWhenBlank() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest(
                "john@example.com",
                "John Updated",
                "",
                "(11) 98888-7777",
                "São Paulo, SP",
                LocalDate.of(1990, 1, 1)
        );

        when(userRepository.findUserById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateUserResponse result = userService.updateUser(1L, request);

        assertEquals("John Updated", result.username());
        assertEquals("john@example.com", result.email());
        assertEquals("encoded123", existingUser.getPassword());
        verify(userRepository, times(1)).save(existingUser);
    }
}
