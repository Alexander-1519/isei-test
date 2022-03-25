package com.example.iseitest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("test");
    }

    @GetMapping
    public ResponseEntity<String> main() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("main page");
    }

    @GetMapping("/ps")
    public ResponseEntity<String> ps() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("ps page");
    }
}
