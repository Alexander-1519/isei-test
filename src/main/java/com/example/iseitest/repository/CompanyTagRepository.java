package com.example.iseitest.repository;

import com.example.iseitest.entity.CompanyTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyTagRepository extends JpaRepository<CompanyTag, Long> {
}
