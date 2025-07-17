package com.example.demo.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateResumeResponse {
    private Long id;
    private String url;
    private Instant createdAt;
    private String createdBy;
}
