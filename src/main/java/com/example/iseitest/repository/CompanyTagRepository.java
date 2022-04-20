package com.example.iseitest.repository;

import com.example.iseitest.entity.CompanyTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyTagRepository extends JpaRepository<CompanyTag, Long> {

    @Query("SELECT t FROM CompanyTag t WHERE t.name LIKE concat('%', upper(:name) , '%')")
    List<CompanyTag> getAllWithFiltering(@Param("name") String name);
}
