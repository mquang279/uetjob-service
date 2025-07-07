package com.example.demo.entity;

import java.time.Instant;

import com.example.demo.service.SecurityService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CompanyReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private int rating;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant createdAt;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant updatedAt;

    private String updatedBy;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Company company;

    public CompanyReview(String title, String description, int rating, User user, Company company) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.user = user;
        this.company = company;
    }

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityService.getCurrentUserEmailLogin();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityService.getCurrentUserEmailLogin();
    }
}
