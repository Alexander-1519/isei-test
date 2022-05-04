package com.example.iseitest.service;

import com.example.iseitest.dto.report.ReportTypeDto;
import com.example.iseitest.entity.Company;
import com.example.iseitest.entity.User;
import com.example.iseitest.entity.UserReport;
import com.example.iseitest.entity.UserReportStatus;
import com.example.iseitest.exception.*;
import com.example.iseitest.repository.CompanyRepository;
import com.example.iseitest.repository.UserReportRepository;
import com.example.iseitest.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserReportService {

    private final FileService fileService;
    private final UserReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final MailService mailService;

    public UserReportService(FileService fileService,
                             UserReportRepository reportRepository,
                             UserRepository userRepository,
                             CompanyRepository companyRepository,
                             MailService mailService) {
        this.fileService = fileService;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.mailService = mailService;
    }

    public UserReport createReport(UserReport userReport, Company company, MultipartFile file, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException(email));

        Company companyByName = companyRepository.findByName(company.getName())
                .orElseThrow(() -> new NoSuchCompanyException(company.getName()));

        String fileName = fileService.save(file);
        String uri = fileService.findByName(fileName).getHttpRequest().getRequestLine().getUri();

        userReport.setImageUrl(uri);
        userReport.setUser(user);
        userReport.setCompany(companyByName);
        userReport.setStatus(UserReportStatus.PENDING_VERIFICATION);

        return reportRepository.save(userReport);
    }

    public UserReport getReport(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new NoSuchUserReportException(id));
    }

    public List<UserReport> getAllReports() {
        return reportRepository.findAll();
    }

    public List<UserReport> getAllReports(Long userId, String companyName, ReportTypeDto reportType) {
        switch (reportType) {
            case USER:
                return reportRepository.findByUserId(userId);
            case COMPANY:
                if (companyName == null || companyName.equals("null")) {
                    return new ArrayList<>();
                }
                return reportRepository.findByCompanyName(companyName);
            case CITY:
                return reportRepository.findByBelongCompanyIsFalse();
        }

        return getAllReports();
    }

    public UserReport changeStatus(UserReportStatus status, Long reportId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException(email));

        UserReport report = reportRepository.findById(reportId).orElseThrow(() -> new NoSuchUserReportException(reportId));

        if(user.getUserRole().getName().name().equals("MODERATOR") && status.equals(UserReportStatus.APPROVED)) {
            mailService.sendEmail(report.getCompany().getEmail(), "NEW REQUEST",
                    "You have new request with id = " + report.getId());
        }

        report.setStatus(status);

        return reportRepository.save(report);
    }
}
