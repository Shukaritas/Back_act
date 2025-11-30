package com.agroapp.platform.plants.domain.model.commands;

import java.time.LocalDateTime;

public record UpdateTaskCommand(
        Long taskId,
        Long fieldId,
        String description,
        LocalDateTime dueDate
) {
}

