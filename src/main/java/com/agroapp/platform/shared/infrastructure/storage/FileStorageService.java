package com.agroapp.platform.shared.infrastructure.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Service for file storage operations.
 * Handles saving files to the local filesystem.
 */
@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    /**
     * Constructor that initializes the storage location.
     * Creates the uploads directory if it doesn't exist.
     *
     * @param uploadDir The directory where files will be stored
     */
    public FileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * Stores a file in the filesystem.
     *
     * @param file The file to store
     * @return The filename of the stored file
     * @throws FileStorageException if the file cannot be stored
     */
    public String storeFile(MultipartFile file) {
        // Validate file
        if (file.isEmpty()) {
            throw new FileStorageException("Failed to store empty file.");
        }

        // Get original filename
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.contains("..")) {
            throw new FileStorageException("Invalid filename: " + originalFilename);
        }

        // Generate unique filename to avoid conflicts
        String fileExtension = getFileExtension(originalFilename);
        String newFilename = UUID.randomUUID() + fileExtension;

        try {
            // Copy file to the target location (replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFilename;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + newFilename + ". Please try again!", ex);
        }
    }

    /**
     * Extracts the file extension from a filename.
     *
     * @param filename The filename
     * @return The file extension (including the dot)
     */
    private String getFileExtension(String filename) {
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return filename.substring(lastIndexOf);
    }

    /**
     * Custom exception for file storage errors.
     */
    public static class FileStorageException extends RuntimeException {
        public FileStorageException(String message) {
            super(message);
        }

        public FileStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

