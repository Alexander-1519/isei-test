package com.example.iseitest.repository;

import com.example.iseitest.entity.UserRole;
import com.example.iseitest.entity.UserRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByName(UserRoleName userRoleName);
}
