package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.commands.UpdateProgressHistoryCommand;
import com.agroapp.platform.plants.interfaces.rest.resources.UpdateProgressHistoryResource;

/**
 * Assembler class to transform UpdateProgressHistoryResource (DTO) to UpdateProgressHistoryCommand.
 * Follows the pattern: [Command]CommandFromResourceAssembler
 * Pure transformation for update operations.
 */
public class UpdateProgressHistoryCommandFromResourceAssembler {

    /**
     * Transforms an UpdateProgressHistoryResource into an UpdateProgressHistoryCommand.
     *
     * @param progressHistoryId The ID of the progress history to update (from path parameter)
     * @param resource The incoming REST resource with update data
     * @return UpdateProgressHistoryCommand to be processed by the domain layer
     */
    public static UpdateProgressHistoryCommand toCommandFromResource(Long progressHistoryId, UpdateProgressHistoryResource resource) {
        return new UpdateProgressHistoryCommand(
                progressHistoryId,
                resource.watered(),
                resource.fertilized(),
                resource.pests()
        );
    }
}

