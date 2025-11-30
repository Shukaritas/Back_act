package com.agroapp.platform.plants.domain.model.commands;

import java.time.LocalDateTime;

public record CreateTaskCommand(
        Long fieldId,
        String description,
        LocalDateTime dueDate
) {
}

