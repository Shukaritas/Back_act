package com.agroapp.platform.shared.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Web MVC Configuration.
 * Configures static resource handling for uploaded files.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    /**
     * Configures static resource handlers.
     * Maps the /uploads/** URL pattern to the physical uploads directory.
     *
     * @param registry ResourceHandlerRegistry to add handlers to
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Create File object for uploads directory
        File uploadPath = new File(uploadDir);

        // Ensure the directory exists
        if (!uploadPath.exists()) {
            boolean created = uploadPath.mkdirs();
            System.out.println("Uploads directory created: " + created);
        }

        // Convert to absolute path with proper file URL format
        String uploadLocation = "file:///" + uploadPath.getAbsolutePath().replace("\\", "/") + "/";

        // Map /uploads/** URL pattern to the physical directory
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadLocation)
                .setCachePeriod(0); // Disable cache for development

        // Log the configuration for debugging
        System.out.println("====================================");
        System.out.println("Static Resource Handler Configured:");
        System.out.println("  URL Pattern: /uploads/**");
        System.out.println("  Physical Location: " + uploadLocation);
        System.out.println("  Absolute Path: " + uploadPath.getAbsolutePath());
        System.out.println("  Directory Exists: " + uploadPath.exists());
        System.out.println("  Is Directory: " + uploadPath.isDirectory());
        System.out.println("====================================");
    }
}

