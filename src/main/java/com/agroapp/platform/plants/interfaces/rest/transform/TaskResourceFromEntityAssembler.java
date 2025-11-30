package com.agroapp.platform.plants.interfaces.rest.transform;

import com.agroapp.platform.plants.domain.model.aggregates.Task;
import com.agroapp.platform.plants.interfaces.rest.resources.TaskResource;

/**
 * Assembler class to transform Task entity to TaskResource (DTO).
 * Follows the pattern: [Entity]ResourceFromEntityAssembler
 * Pure transformation, no business logic.
 */
public class TaskResourceFromEntityAssembler {

    /**
     * Transforms a Task aggregate into a TaskResource.
     *
     * @param task The Task aggregate root
     * @return TaskResource for REST API response
     */
    public static TaskResource toResourceFromEntity(Task task) {
        return new TaskResource(
                task.getId(),
                task.getFieldId(),
                task.getDescription(),
                task.getDueDate(),
                task.getCreatedAt() != null ? task.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null,
                task.getUpdatedAt() != null ? task.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null
        );
    }
}

