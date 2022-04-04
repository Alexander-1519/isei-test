package com.example.iseitest.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_report")
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private Double latitude;
    private Double longitude;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean belongCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getBelongCompany() {
        return belongCompany;
    }

    public void setBelongCompany(Boolean belongCompany) {
        this.belongCompany = belongCompany;
    }
}
