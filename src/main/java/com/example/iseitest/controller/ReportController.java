package com.example.iseitest.controller;

import com.example.iseitest.dto.report.ReportOutputDto;
import com.example.iseitest.dto.report.ReportTypeDto;
import com.example.iseitest.entity.Company;
import com.example.iseitest.entity.UserReport;
import com.example.iseitest.entity.UserReportStatus;
import com.example.iseitest.mapper.UserReportMapper;
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
    private final UserReportMapper userReportMapper;

    public ReportController(UserReportService reportService, UserReportMapper userReportMapper) {
        this.reportService = reportService;
        this.userReportMapper = userReportMapper;
    }

    @PostMapping("/reports")
    public ResponseEntity<ReportOutputDto> saveReport(@RequestPart("file") MultipartFile file,
                                                 @RequestPart("report") UserReport report,
                                                 @RequestPart("company") Company company,
                                                 Principal principal) {
        UserReport userReport = reportService.createReport(report, company, file, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userReportMapper.toOutputDto(userReport));
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<UserReport> getReport(@PathVariable Long id, Principal principal) {
        UserReport report = reportService.getReport(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(report);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ReportOutputDto>> getAllReport(@RequestParam(required = false) Long userId,
                                                              @RequestParam(required = false) String companyName,
                                                              @RequestParam(required = false) ReportTypeDto type,
                                                              Principal principal) {
        List<UserReport> allReports = reportService.getAllReports();

        return ResponseEntity.status(HttpStatus.OK)
                .body(userReportMapper.toListOutput(allReports));
    }

    @PutMapping("/reports/{id}")
    public ResponseEntity<UserReport> changeReportStatus(@PathVariable Long id,
                                                         @RequestParam UserReportStatus status,
                                                         Principal principal) {
        UserReport userReport = reportService.changeStatus(status, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK)
                .body(userReport);
    }
}