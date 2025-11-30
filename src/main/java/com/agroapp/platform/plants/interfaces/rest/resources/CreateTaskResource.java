package com.agroapp.platform.plants.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateTaskResource(
        Long fieldId,
        String description,
        LocalDateTime dueDate
) {
}

