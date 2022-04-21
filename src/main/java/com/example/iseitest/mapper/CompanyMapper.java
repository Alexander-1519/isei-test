package com.example.iseitest.mapper;

import com.example.iseitest.dto.company.CompanyOutputDto;
import com.example.iseitest.entity.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        config = BaseMapperConfig.class
)
public interface CompanyMapper {

    List<CompanyOutputDto> toListOutputDto(List<Company> companies);
}
