package com.example.iseitest.controller;

import com.example.iseitest.entity.User;
import com.example.iseitest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK)
                .body(users);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user, Principal principal) {
        User updatedUser = userService.updateUser(user, principal.getName(), id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedUser);
    }
}
