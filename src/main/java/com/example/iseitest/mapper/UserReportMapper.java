package com.example.iseitest.mapper;

import com.example.iseitest.dto.report.ReportOutputDto;
import com.example.iseitest.entity.UserReport;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        config = BaseMapperConfig.class
)
public interface UserReportMapper {

    List<ReportOutputDto> toListOutput(List<UserReport> userReports);

    ReportOutputDto toOutputDto(UserReport userReport);
}
