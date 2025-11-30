package com.agroapp.platform.plants.domain.model.commands;

import java.time.LocalDateTime;

public record UpdateProgressHistoryCommand(
        Long progressHistoryId,
        LocalDateTime watered,
        LocalDateTime fertilized,
        LocalDateTime pests
) {
}

