package com.supamenu.backend.file_storage;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocalFileStorage implements FileStorageService {

    private final FileStorageProperties fileStorageProperties;
    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            log.info("Created directory for storing files at {}", this.fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file, String directory) throws FileStorageException {
        // Check if the file size is empty.
        if (file.isEmpty()) {
            throw new FileStorageException("Failed to store empty file.");
        }

        // Validate file size
        if (file.getSize() > fileStorageProperties.getMaxFileSize()) {
            throw new FileStorageException("File size exceeds maximum allowed size of "
                    + (fileStorageProperties.getMaxFileSize() / (1024 * 1024)) + "MB");
        }

        // Validate file extension
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = getFileExtension(originalFileName);

        if (!fileStorageProperties.getAllowedExtensions().contains(fileExtension.toLowerCase())) {
            throw new FileStorageException("File extension " + fileExtension + " is not allowed. Allowed extensions are: " + fileStorageProperties.getAllowedExtensions());
        }

        // Generate a unique file name to prevent the duplicates.
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;

        try {
            Path targetDirectory = this.fileStorageLocation.resolve(directory);
            if (!Files.exists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }

            // create the final path where the file will be stored.
            Path targetLocation = targetDirectory.resolve(newFileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            log.info("Stored the file {} in directory {}", newFileName, directory);

            // Return the relative path of the file.
            return directory + File.separator + newFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store the file " + originalFileName + ". Please try again!", ex);
        }
    }

    @Override
    public boolean deleteFile(String relativePath) {
        if (relativePath.isEmpty())
            return false;
        try {
            Path filePath = fileStorageLocation.resolve(relativePath);
            return Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            log.error("Could not delete the file", ex);
            return false;
        }
    }

    @Override
    public String getFileUrl(String relativePath) {
        if (relativePath.isEmpty())
            return null;
        return fileStorageProperties.getBaseUrl() + File.separator + relativePath;
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            throw new FileStorageException("file has no extension " + fileName);
        }
    }
}