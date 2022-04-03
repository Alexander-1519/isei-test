package com.example.iseitest.service;

import com.example.iseitest.entity.User;
import com.example.iseitest.entity.UserReport;
import com.example.iseitest.exception.NoSuchUserException;
import com.example.iseitest.exception.NoSuchUserReportException;
import com.example.iseitest.repository.UserReportRepository;
import com.example.iseitest.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserReportService {

    private final FileService fileService;
    private final UserReportRepository reportRepository;
    private final UserRepository userRepository;

    public UserReportService(FileService fileService,
                             UserReportRepository reportRepository,
                             UserRepository userRepository) {
        this.fileService = fileService;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public UserReport createReport(UserReport userReport, MultipartFile file, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException(email));

        String fileName = fileService.save(file);
        String uri = fileService.findByName(fileName).getHttpRequest().getRequestLine().getUri();

        userReport.setImageUrl(uri);
        userReport.setUser(user);

        return reportRepository.save(userReport);
    }

    public UserReport getReport(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new NoSuchUserReportException(id));
    }

    public List<UserReport> getAllReports() {
        return reportRepository.findAll();
    }
}
