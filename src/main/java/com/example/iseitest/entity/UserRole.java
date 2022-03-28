package com.example.iseitest.entity;

import javax.persistence.*;

@Entity
@Table
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRoleName name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRoleName getName() {
        return name;
    }

    public void setName(UserRoleName name) {
        this.name = name;
    }
}
