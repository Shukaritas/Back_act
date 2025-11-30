package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.CreateCropFieldCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateCropFieldResource;

/**
 * Assembler class to transform CreateCropFieldResource (DTO) to CreateCropFieldCommand.
 * Follows the pattern: [Command]CommandFromResourceAssembler
 * Pure transformation for write operations.
 */
public class CreateCropFieldCommandFromResourceAssembler {

    /**
     * Transforms a CreateCropFieldResource into a CreateCropFieldCommand.
     *
     * @param resource The incoming REST resource
     * @return CreateCropFieldCommand to be processed by the domain layer
     */
    public static CreateCropFieldCommand toCommandFromResource(CreateCropFieldResource resource) {
        return new CreateCropFieldCommand(
                resource.fieldId(),
                resource.crop(),
                resource.soilType(),
                resource.sunlight(),
                resource.watering(),
                resource.plantingDate(),
                resource.harvestDate(),
                resource.status()
        );
    }
}

