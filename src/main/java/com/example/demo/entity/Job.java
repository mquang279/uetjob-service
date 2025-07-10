package com.example.demo.entity;

import java.time.Instant;
import java.util.List;

import com.example.demo.entity.enums.JobCategory;
import com.example.demo.entity.enums.JobLevel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant endDate;

    private double minSalary;

    private double maxSalary;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private JobCategory category;

    @Enumerated(EnumType.STRING)
    private JobLevel level;

    private boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant createdAt;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Instant updatedAt;

    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnoreProperties(value = { "jobs" })
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "job_skill", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @JsonIgnoreProperties(value = { "jobs" })
    private List<Skill> skills;
}
