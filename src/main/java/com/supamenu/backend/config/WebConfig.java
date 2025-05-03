package com.supamenu.backend.config;

import com.supamenu.backend.file_storage.FileStorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final FileStorageProperties fileStorageProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Extract the path from the base URL.
        String urlPath = fileStorageProperties.getBaseUrl().substring(fileStorageProperties.getBaseUrl().lastIndexOf("/"));

        // Map URL path to the physical directory.
        registry.addResourceHandler( urlPath + "/**")
                        .addResourceLocations("file:" + fileStorageProperties.getUploadDir() + File.separator);
    }

}
