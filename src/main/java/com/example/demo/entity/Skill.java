package com.example.demo.entity;

import java.time.Instant;
import java.util.List;

import com.example.demo.service.SecurityService;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant createdAt;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant updatedAt;

    private String updatedBy;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private List<Job> jobs;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityService.getCurrentUserEmailLogin();
    }

    @PostUpdate
    public void handleBeforeUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityService.getCurrentUserEmailLogin();
    }
}