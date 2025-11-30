package com.agroapp.platform.plants.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * ProgressHistory Resource for REST API responses.
 * Matches the OpenAPI schema definition exactly.
 */
public record ProgressHistoryResource(
        Long id,
        Long fieldId,
        LocalDateTime watered,
        LocalDateTime fertilized,
        LocalDateTime pests,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}

