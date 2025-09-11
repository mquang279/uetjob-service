package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PresignedUrlRequest {
    @NotBlank(message = "Folder name is required")
    @Size(min = 1, max = 100, message = "Folder name must be between 1 and 100 characters")
    private String folder;

    @NotBlank(message = "File name is required")
    @Size(min = 1, max = 255, message = "File name must be between 1 and 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "File name can only contain letters, numbers, dots, underscores, and hyphens")
    private String fileName;
}
