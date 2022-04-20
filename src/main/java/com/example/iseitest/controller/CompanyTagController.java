package com.example.iseitest.controller;

import com.example.iseitest.entity.CompanyTag;
import com.example.iseitest.service.CompanyTagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyTagController {

    private final CompanyTagService tagService;

    public CompanyTagController(CompanyTagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tags")
    public ResponseEntity<CompanyTag> createTag(@RequestBody CompanyTag companyTag) {
        CompanyTag tag = tagService.createTag(companyTag.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tag);
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<CompanyTag> getTagById(@PathVariable Long id) {
        CompanyTag tag = tagService.getTagById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(tag);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<CompanyTag>> getAll(@RequestParam(required = false, defaultValue = "") String name) {
        List<CompanyTag> tags = tagService.getAll(name);

        return ResponseEntity.status(HttpStatus.OK)
                .body(tags);
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Void> deleteTagById(@PathVariable Long id) {
        tagService.deleteTag(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
