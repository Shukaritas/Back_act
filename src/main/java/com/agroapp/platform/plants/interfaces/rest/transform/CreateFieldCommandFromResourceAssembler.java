package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.CreateFieldCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateFieldResource;

/**
 * Assembler class to transform CreateFieldResource (DTO) to CreateFieldCommand.
 * Follows the pattern: [Command]CommandFromResourceAssembler
 * Pure transformation for write operations.
 */
public class CreateFieldCommandFromResourceAssembler {

    /**
     * Transforms a CreateFieldResource into a CreateFieldCommand.
     *
     * @param resource The incoming REST resource
     * @return CreateFieldCommand to be processed by the domain layer
     */
    public static CreateFieldCommand toCommandFromResource(CreateFieldResource resource) {
        return new CreateFieldCommand(
                resource.userId(),
                resource.imageUrl(),
                resource.name(),
                resource.location(),
                resource.fieldSize()
        );
    }
}

