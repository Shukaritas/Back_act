package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.UpdateCropFieldCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.UpdateCropFieldResource;

/**
 * Assembler class to transform UpdateCropFieldResource (DTO) to UpdateCropFieldCommand.
 * Follows the pattern: [Command]CommandFromResourceAssembler
 * Pure transformation for update operations.
 */
public class UpdateCropFieldCommandFromResourceAssembler {

    /**
     * Transforms an UpdateCropFieldResource into an UpdateCropFieldCommand.
     *
     * @param resource The incoming REST resource with update data
     * @return UpdateCropFieldCommand to be processed by the domain layer
     */
    public static UpdateCropFieldCommand toCommandFromResource(UpdateCropFieldResource resource) {
        return new UpdateCropFieldCommand(
                resource.cropFieldId(),
                resource.crop(),
                resource.status()
        );
    }
}

