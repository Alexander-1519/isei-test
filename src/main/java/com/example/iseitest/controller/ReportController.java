package com.example.iseitest.controller;

import com.example.iseitest.entity.UserReport;
import com.example.iseitest.service.UserReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

    private final UserReportService reportService;

    public ReportController(UserReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reports")
    public ResponseEntity<UserReport> saveReport(@RequestPart("file") MultipartFile file,
                                                 @RequestPart("report") UserReport report,
                                                 Principal principal) {
        UserReport userReport = reportService.createReport(report, file, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userReport);
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<UserReport> getReport(@PathVariable Long id, Principal principal) {
        UserReport report = reportService.getReport(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(report);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<UserReport>> getAllReport(Principal principal) {
        List<UserReport> allReports = reportService.getAllReports();

        return ResponseEntity.status(HttpStatus.OK)
                .body(allReports);
    }
}