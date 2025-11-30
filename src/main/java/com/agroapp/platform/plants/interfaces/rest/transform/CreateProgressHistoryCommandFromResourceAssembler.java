package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.CreateProgressHistoryCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateProgressHistoryResource;

/**
 * Assembler class to transform CreateProgressHistoryResource (DTO) to CreateProgressHistoryCommand.
 * Follows the pattern: [Command]CommandFromResourceAssembler
 * Pure transformation for write operations.
 */
public class CreateProgressHistoryCommandFromResourceAssembler {

    /**
     * Transforms a CreateProgressHistoryResource into a CreateProgressHistoryCommand.
     *
     * @param resource The incoming REST resource
     * @return CreateProgressHistoryCommand to be processed by the domain layer
     */
    public static CreateProgressHistoryCommand toCommandFromResource(CreateProgressHistoryResource resource) {
        return new CreateProgressHistoryCommand(
                resource.fieldId(),
                resource.watered(),
                resource.fertilized(),
                resource.pests()
        );
    }
}

