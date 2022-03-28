package com.example.iseitest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.iseitest.*")
public class IseiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IseiTestApplication.class, args);
    }

}
