package com.agroapp.platform.plants.interfaces.rest.resources;

import java.time.LocalDateTime;

public record TaskResource(
        Long id,
        Long fieldId,
        String description,
        LocalDateTime dueDate,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}

