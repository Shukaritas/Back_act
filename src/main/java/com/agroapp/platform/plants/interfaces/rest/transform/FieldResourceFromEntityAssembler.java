package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.aggregates.Field;
import com.agroapp.platform.plants.interfaces.rest.resources.FieldResource;

import java.util.List;

/**
 * Assembler class to transform Field entity to FieldResource (DTO).
 * Follows the pattern: [Entity]ResourceFromEntityAssembler
 * Pure transformation, no business logic or service calls.
 */
public class FieldResourceFromEntityAssembler {

    /**
     * Transforms a Field entity into a FieldResource.
     *
     * @param field The Field aggregate root
     * @param progressHistoryId The associated ProgressHistory ID (nullable)
     * @param cropFieldId The associated CropField ID (nullable)
     * @param taskIds List of associated Task IDs
     * @return FieldResource for REST API response
     */
    public static FieldResource toResourceFromEntity(Field field,
                                                      Long progressHistoryId,
                                                      Long cropFieldId,
                                                      List<Long> taskIds) {

        return new FieldResource(
                field.getId(),
                field.getUserId(),
                field.getImageUrl(),
                field.getName(),
                field.getLocation(),
                field.getFieldSize(),
                progressHistoryId,
                cropFieldId,
                taskIds,
                field.getCreatedAt() != null ? field.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                field.getUpdatedAt() != null ? field.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}

