package com.edugamefy.backend.controller;

import com.edugamefy.backend.Entity.User;
import com.edugamefy.backend.dto.ResponseWrapper;
import com.edugamefy.backend.service.UserService;
import com.edugamefy.backend.viewModels.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<?>> createUser(@RequestBody CreateNewUserRequest user) {
        try {
            CreateNewUserResponse newUser =  userService.createUser(user);
            ResponseWrapper<CreateNewUserResponse> response = new ResponseWrapper<>(newUser, true);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<String> response = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers (){
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                ))
                .toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> getUsersById (@PathVariable Long id) throws Exception {
        User user = userService.findUserById(id);
        UserResponse response = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest userRequest) throws Exception {
            UpdateUserResponse updated = this.userService.updateUser(id, userRequest);
            return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws Exception {
            this.userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
