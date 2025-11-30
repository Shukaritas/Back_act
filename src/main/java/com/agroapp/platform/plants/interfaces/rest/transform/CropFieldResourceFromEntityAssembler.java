package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.entities.CropField;
import com.agroapp.platform.plants.interfaces.rest.resources.CropFieldResource;

/**
 * Assembler class to transform CropField entity to CropFieldResource (DTO).
 * Follows the pattern: [Entity]ResourceFromEntityAssembler
 * Pure transformation, no business logic.
 */
public class CropFieldResourceFromEntityAssembler {

    /**
     * Transforms a CropField entity into a CropFieldResource.
     *
     * @param cropField The CropField entity (1:1 with Field)
     * @return CropFieldResource for REST API response
     */
    public static CropFieldResource toResourceFromEntity(CropField cropField) {
        return new CropFieldResource(
                cropField.getId(),
                cropField.getFieldId(),
                cropField.getCrop(),
                cropField.getSoilType(),
                cropField.getSunlight(),
                cropField.getWatering(),
                cropField.getPlantingDate(),
                cropField.getHarvestDate(),
                cropField.getStatus(),
                cropField.getCreatedAt() != null ? cropField.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                cropField.getUpdatedAt() != null ? cropField.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}

