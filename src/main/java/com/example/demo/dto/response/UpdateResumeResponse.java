package com.example.demo.dto.response;

import java.time.Instant;

import com.example.demo.entity.enums.ResumeStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResumeResponse {
    private Long id;
    private String url;
    private ResumeStatus status;
    private Instant updatedAt;
    private String updatedBy;
}
