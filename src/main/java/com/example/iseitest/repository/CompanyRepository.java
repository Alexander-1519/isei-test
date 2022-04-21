package com.example.iseitest.repository;

import com.example.iseitest.entity.Company;
import com.example.iseitest.entity.CompanyTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByName(String name);

    Boolean existsByName(String name);

    @Query("SELECT DISTINCT c FROM Company c JOIN c.tags t WHERE t IN :tags")
    List<Company> findByTagsIn(@Param("tags") Set<CompanyTag> tags);

//    @Query("SELECT DISTINCT c FROM Company c JOIN c.tags t WHERE t.name LIKE :name")
//    List<Company> findByTagsNameContaining(@Param("name") String name);

//    @Query("SELECT c FROM Company c WHERE c.tags")
//    List<Company> findByTagsContaining(CompanyTag tag);
}
