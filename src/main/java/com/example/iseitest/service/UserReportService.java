package com.example.iseitest.service;

import com.example.iseitest.dto.report.ReportTypeDto;
import com.example.iseitest.entity.Company;
import com.example.iseitest.entity.User;
import com.example.iseitest.entity.UserReport;
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

    public UserReportService(FileService fileService,
                             UserReportRepository reportRepository,
                             UserRepository userRepository,
                             CompanyRepository companyRepository) {
        this.fileService = fileService;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public UserReport createReport(UserReport userReport, Company company, MultipartFile file, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException(email));

//        if(user.getCompany() == null && userReport.getBelongCompany()) {
//            throw ExceptionBuilder.builder(Code.UNEXPECTED)
//                    .withMessage("Can't save report to user with no company")
//                    .build(IseiException.class);
//        }

         Company companyByName = companyRepository.findByName(company.getName())
                 .orElseThrow(() -> new NoSuchCompanyException(company.getName()));

        String fileName = fileService.save(file);
        String uri = fileService.findByName(fileName).getHttpRequest().getRequestLine().getUri();

        userReport.setImageUrl(uri);
        userReport.setUser(user);
        userReport.setCompany(companyByName);

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
                if(companyName == null || companyName.equals("null")) {
                    return new ArrayList<>();
                }
                return reportRepository.findByCompanyName(companyName);
            case CITY:
                return reportRepository.findByBelongCompanyIsFalse();
        }

        return getAllReports();
    }
}
