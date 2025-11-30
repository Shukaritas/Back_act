package com.agroapp.platform.plants.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.List;

public record FieldResource(
        Long id,
        Long userId,
        String imageUrl,
        String name,
        String location,
        String fieldSize,
        Long progressHistoryId,
        Long cropFieldId,
        List<Long> taskIds,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}

