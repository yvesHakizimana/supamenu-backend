package com.supamenu.backend.file_storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "app.file-storage")
@Data
public class FileStorageProperties {

    private String uploadDir = "uploads";
    private String baseUrl = "http://localhost:8080/files";

    // Image Validation Properties
    private long maxFileSize = 5 * 1024 * 1024; // 5 MB
    private Set<String> allowedExtensions = Set.of("jpg", "jpeg", "png", "gif");
}