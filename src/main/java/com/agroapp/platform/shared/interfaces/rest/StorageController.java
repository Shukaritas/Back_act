package com.agroapp.platform.shared.interfaces.rest;

import com.agroapp.platform.shared.infrastructure.storage.FileStorageService;
import com.agroapp.platform.shared.interfaces.rest.resources.FileUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST Controller for file storage operations.
 * Provides endpoints for uploading files and retrieving their URLs.
 */
@RestController
@RequestMapping("/api/v1/storage")
@Tag(name = "Storage", description = "File storage operations for images and documents")
public class StorageController {

    private final FileStorageService fileStorageService;

    public StorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Uploads a file and returns its public URL.
     *
     * @param file The file to upload
     * @return ResponseEntity with the file URL
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Upload a file",
        description = "Uploads an image or document and returns its public URL. The file will be stored in the server's filesystem."
    )
    @ApiResponse(
        responseCode = "200",
        description = "File uploaded successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = FileUploadResponse.class)
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid file or file upload failed"
    )
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Store the file
            String filename = fileStorageService.storeFile(file);

            // Build the public URL (e.g., http://localhost:8080/uploads/filename.jpg)
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();

            // Log the generated URL for debugging
            System.out.println("File uploaded successfully:");
            System.out.println("  Filename: " + filename);
            System.out.println("  Public URL: " + fileDownloadUri);

            // Return the URL
            return ResponseEntity.ok(new FileUploadResponse(fileDownloadUri));
        } catch (FileStorageService.FileStorageException ex) {
            System.err.println("File upload failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FileUploadResponse("Error: " + ex.getMessage()));
        }
    }

    /**
     * Lists all uploaded files (for debugging purposes).
     *
     * @return List of uploaded file names
     */
    @GetMapping("/list")
    @Operation(
        summary = "List uploaded files",
        description = "Lists all files in the uploads directory (for debugging/testing purposes)"
    )
    public ResponseEntity<?> listFiles() {
        try {
            java.io.File uploadsDir = new java.io.File("uploads");
            if (!uploadsDir.exists() || !uploadsDir.isDirectory()) {
                return ResponseEntity.ok(java.util.Map.of(
                    "message", "Uploads directory does not exist",
                    "path", uploadsDir.getAbsolutePath()
                ));
            }

            java.io.File[] files = uploadsDir.listFiles();
            java.util.List<String> fileNames = new java.util.ArrayList<>();

            if (files != null) {
                for (java.io.File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            }

            return ResponseEntity.ok(java.util.Map.of(
                "directory", uploadsDir.getAbsolutePath(),
                "exists", uploadsDir.exists(),
                "fileCount", fileNames.size(),
                "files", fileNames
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of("error", ex.getMessage()));
        }
    }
}

