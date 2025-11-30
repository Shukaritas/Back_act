package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.entities.ProgressHistory;
import com.agroapp.platform.plants.interfaces.rest.resources.ProgressHistoryResource;

/**
 * Assembler class to transform ProgressHistory entity to ProgressHistoryResource (DTO).
 * Follows the pattern: [Entity]ResourceFromEntityAssembler
 * Pure transformation, no business logic.
 */
public class ProgressHistoryResourceFromEntityAssembler {

    /**
     * Transforms a ProgressHistory entity into a ProgressHistoryResource.
     *
     * @param progressHistory The ProgressHistory entity (1:1 with Field)
     * @return ProgressHistoryResource for REST API response
     */
    public static ProgressHistoryResource toResourceFromEntity(ProgressHistory progressHistory) {
        return new ProgressHistoryResource(
                progressHistory.getId(),
                progressHistory.getFieldId(),
                progressHistory.getWatered(),
                progressHistory.getFertilized(),
                progressHistory.getPests(),
                progressHistory.getCreatedAt() != null ? progressHistory.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                progressHistory.getUpdatedAt() != null ? progressHistory.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}

