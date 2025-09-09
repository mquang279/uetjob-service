package com.example.demo.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

import com.example.demo.exception.StorageException;
import com.example.demo.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${storage.directory}")
    private String path;

    private Path root;

    // TODO: move file size to application properties
    private final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    private final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "jpg", "jpeg", "png", "pdf", "doc", "docx");
    private final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "image/jpeg", "image/png",
            "application/pdf", "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    public StorageServiceImpl() {

    }

    @PostConstruct
    public void initialize() {
        this.root = Paths.get(path);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new StorageException(
                    "File size exceeds maximum allowed size of " + (MAX_FILE_SIZE / 1024 / 1024) + "MB");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new StorageException("File must have a name");
        }

        String fileExtension = getFileExtension(originalFilename).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            throw new StorageException(
                    "File extension '" + fileExtension + "' is not allowed. Allowed extensions: " + ALLOWED_EXTENSIONS);
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new StorageException(
                    "File type '" + contentType + "' is not allowed. Allowed types: " + ALLOWED_MIME_TYPES);
        }
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1);
    }

    private void formatFileName(MultipartFile file) {

    }

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage");
        }
    }

    @Override
    public String store(MultipartFile file, String folder, String fileName) {
        try {
            validateFile(file);

            createFolder(folder);

            String originalFileName = file.getOriginalFilename();
            fileName = fileName + originalFileName.substring(originalFileName.lastIndexOf('.'));

            Path destinationFile = this.root.resolve(folder).resolve(fileName)
                    .normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return fileName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files");
        }
    }

    @Override
    public Path load(String filename) {
        return root.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public void createFolder(String folderName) {
        try {
            if (folderName == null || folderName.trim().isEmpty()) {
                throw new StorageException("Folder name cannot be null or empty");
            }

            String sanitizedFolderName = folderName.replaceAll("[^a-zA-Z0-9._-]", "_");

            Path folderPath = this.root.resolve(sanitizedFolderName).normalize().toAbsolutePath();

            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to create folder: " + folderName, e);
        }
    }

}
