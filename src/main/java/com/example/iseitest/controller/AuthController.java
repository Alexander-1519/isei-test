package com.example.iseitest.controller;

import com.example.iseitest.dto.UserOutputDto;
import com.example.iseitest.dto.UserRegistrationDto;
import com.example.iseitest.dto.UserSignInInputDto;
import com.example.iseitest.dto.UserSignInOutputDto;
import com.example.iseitest.entity.User;
import com.example.iseitest.mapper.UserMapper;
import com.example.iseitest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserOutputDto> createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        User createdUser = userService.createUser(userMapper.toUser(userRegistrationDto));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toUserOutputDto(createdUser));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserSignInOutputDto> sigIn(@RequestBody UserSignInInputDto inputDto) {
        String jwt = userService.login(inputDto.getEmail(), inputDto.getPassword());

        UserSignInOutputDto outputDto = new UserSignInOutputDto();
        outputDto.setJwt(jwt);

        return ResponseEntity.status(HttpStatus.OK)
                .body(outputDto);
    }
}
