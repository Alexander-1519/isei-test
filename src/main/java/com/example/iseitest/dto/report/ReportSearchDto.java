package com.example.iseitest.dto.report;

public class ReportSearchDto {

    private Long userId;

    private String companyName;

    private ReportTypeDto type;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ReportTypeDto getType() {
        return type;
    }

    public void setType(ReportTypeDto type) {
        this.type = type;
    }
}
