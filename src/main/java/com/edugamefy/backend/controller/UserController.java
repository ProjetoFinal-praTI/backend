package com.edugamefy.backend.controller;

import com.edugamefy.backend.Entity.User;
import com.edugamefy.backend.service.UserService;

import com.edugamefy.backend.viewModels.users.CreateNewUserRequest;
import com.edugamefy.backend.viewModels.users.CreateNewUserResponse;
import com.edugamefy.backend.viewModels.balance.NewBalanceRequest;
import com.edugamefy.backend.viewModels.balance.NewBalanceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<CreateNewUserResponse> createUser(@RequestBody CreateNewUserRequest user) throws Exception {
        CreateNewUserResponse newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers (){
        List<User> users = this.userService.getAllUsers();
        return  new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUsersById (@PathVariable Long id) throws Exception {
        User user = this.userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{id}/addBalance")
    public ResponseEntity<?> addBalance(@PathVariable Long id, @RequestBody NewBalanceRequest balance) throws Exception {
        try {
            NewBalanceResponse newBalance = this.userService.addBalance(id, balance);
            return new ResponseEntity<>(newBalance, HttpStatus.CREATED);
        }catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}
