package com.example.iseitest.controller;

import com.example.iseitest.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private final FileService fileService;

    public ImageController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/images")
    public void saveImage(@RequestBody MultipartFile file) {
        fileService.save(file);
    }

}
