package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.UpdateTaskCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.EditTaskResource;

/**
 * Assembler class to transform EditTaskResource (DTO) to UpdateTaskCommand.
 * Follows the pattern: [Command]CommandFromResourceAssembler
 * Pure transformation for update operations.
 */
public class UpdateTaskCommandFromResourceAssembler {

    /**
     * Transforms an EditTaskResource into an UpdateTaskCommand.
     *
     * @param taskId The ID of the task to update (from path parameter)
     * @param resource The incoming REST resource with update data
     * @return UpdateTaskCommand to be processed by the domain layer
     */
    public static UpdateTaskCommand toCommandFromResource(Long taskId, EditTaskResource resource) {
        return new UpdateTaskCommand(
                taskId,
                resource.fieldId(),
                resource.description(),
                resource.dueDate()
        );
    }
}

