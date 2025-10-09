package com.edugamefy.backend.controller;

import com.edugamefy.backend.Entity.User;
import com.edugamefy.backend.dto.ResponseWrapper;
import com.edugamefy.backend.dto.UserDTO;
import com.edugamefy.backend.service.UserService;

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
    public ResponseEntity<ResponseWrapper<?>> createUser(@RequestBody User user) {
        try {
            UserDTO userDTO = userService.createUser(user);
            ResponseWrapper<UserDTO> response = new ResponseWrapper<>(userDTO, true);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<String> response = new ResponseWrapper<>(e.getMessage(), false);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }
}