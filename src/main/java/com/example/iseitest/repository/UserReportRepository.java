package com.example.iseitest.repository;

import com.example.iseitest.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    @Query("FROM UserReport ur WHERE ur.user.id = :id")
    List<UserReport> findByUserId(@Param("id") Long id);

    @Query("FROM UserReport ur WHERE ur.belongCompany = false")
    List<UserReport> findByBelongCompanyIsFalse();

    @Query("FROM UserReport ur WHERE ur.belongCompany = true AND ur.user.company.name = :companyName")
    List<UserReport> findByCompanyName(@Param("companyName") String companyName);
}
