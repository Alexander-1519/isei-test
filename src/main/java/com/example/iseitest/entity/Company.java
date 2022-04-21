package com.example.iseitest.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "companies", cascade = CascadeType.ALL)
    private Set<CompanyTag> tags = new HashSet<>();

    public void addTag(CompanyTag companyTag) {
        if(tags == null) {
            tags = new HashSet<>();
        }
        tags.add(companyTag);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CompanyTag> getTags() {
        return tags;
    }

    public void setTags(Set<CompanyTag> tags) {
        this.tags = tags;
    }
}