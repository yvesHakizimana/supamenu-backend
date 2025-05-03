package com.supamenu.backend.file_storage;


import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    /**
     * Store the file and return its relative path.
     * */
    String storeFile(MultipartFile file, String directory) throws FileStorageException;

    /**
     * Delete the file by its relative path.
     * */
    boolean deleteFile(String relativePath);

    /**
     * Get the full URL for accessing the file.
     * */
    String getFileUrl(String relativePath);


}
