package com.maisfinanca.backend.Tests.Controller;

import com.maisfinanca.backend.entity.User;
import com.maisfinanca.backend.controller.UserController;
import com.maisfinanca.backend.dto.ResponseWrapper;
import com.maisfinanca.backend.service.UserService;
import com.maisfinanca.backend.dto.User.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldReturnOk_whenUserIsCreated() {
        // Arrange
        CreateNewUserRequest request = new CreateNewUserRequest("john@example.com","John", "123");
        CreateNewUserResponse mockResponse = new CreateNewUserResponse("john@example.com", "John");

        when(userService.createUser(request)).thenReturn(mockResponse);

        // Act
        ResponseEntity<ResponseWrapper<?>> response = userController.createUser(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(mockResponse, response.getBody().getData());
        verify(userService, times(1)).createUser(request);
    }

    @Test
    void createUser_shouldReturnBadRequest_whenThrowsIllegalArgumentException() {
        // Arrange
        CreateNewUserRequest request = new CreateNewUserRequest("john@example.com", "John", "123");
        when(userService.createUser(request)).thenThrow(new IllegalArgumentException("Email já cadastrado"));

        // Act
        ResponseEntity<ResponseWrapper<?>> response = userController.createUser(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Email já cadastrado", response.getBody().getErrorMessage());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Arrange
        User user1 = new User(
                1L,
                "john@example.com",
                "John",
                "123",
                null,
                null,
                null
        );

        User user2 = new User(
                2L,
                "jane@example.com",
                "Jane",
                "456",
                null,
                null,
                null
        );

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        // Act
        ResponseEntity<List<UserResponse>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUsersById_shouldReturnUser() throws Exception {
        // Arrange
        User user = new User(1L, "john@example.com","John","123", null, null, null);
        when(userService.findUserById(1L)).thenReturn(user);

        // Act
        ResponseEntity<UserResponse> response = userController.getUsersById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().username());
        assertEquals("john@example.com", response.getBody().email());
        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        // Arrange
        UpdateUserRequest request = new UpdateUserRequest( "new@example.com", "NewName", "123", null, null, null);
        UpdateUserResponse mockResponse = new UpdateUserResponse(1L, "NewName", "new@example.com", null, null, null);

        when(userService.updateUser(1L, request)).thenReturn(mockResponse);

        // Act
        ResponseEntity<?> response = userController.updateUser(1L, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(userService, times(1)).updateUser(1L, request);
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        // Act
        ResponseEntity<?> response = userController.deleteUser(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1L);
    }
}

