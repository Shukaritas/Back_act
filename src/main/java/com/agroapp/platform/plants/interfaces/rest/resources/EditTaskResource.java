package com.agroapp.platform.plants.interfaces.rest.resources;

import java.time.LocalDateTime;

public record EditTaskResource(
        Long fieldId,
        String description,
        LocalDateTime dueDate
) {
}

