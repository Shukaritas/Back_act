package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.CreateTaskCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.CreateTaskResource;

/**
 * Assembler class to transform CreateTaskResource (DTO) to CreateTaskCommand.
 * Follows the pattern: [Command]CommandFromResourceAssembler
 * Pure transformation for write operations.
 */
public class CreateTaskCommandFromResourceAssembler {

    /**
     * Transforms a CreateTaskResource into a CreateTaskCommand.
     *
     * @param resource The incoming REST resource
     * @return CreateTaskCommand to be processed by the domain layer
     */
    public static CreateTaskCommand toCommandFromResource(CreateTaskResource resource) {
        return new CreateTaskCommand(
                resource.fieldId(),
                resource.description(),
                resource.dueDate()
        );
    }
}

