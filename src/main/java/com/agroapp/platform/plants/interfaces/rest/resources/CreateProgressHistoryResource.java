package com.agroapp.platform.plants.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateProgressHistoryResource(
        Long fieldId,
        LocalDateTime watered,
        LocalDateTime fertilized,
        LocalDateTime pests
) {
}

