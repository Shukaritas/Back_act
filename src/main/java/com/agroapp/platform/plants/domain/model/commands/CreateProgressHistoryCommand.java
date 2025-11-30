package com.agroapp.platform.plants.domain.model.commands;

import java.time.LocalDateTime;

public record CreateProgressHistoryCommand(
        Long fieldId,
        LocalDateTime watered,
        LocalDateTime fertilized,
        LocalDateTime pests
) {
}

