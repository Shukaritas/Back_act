package com.agroapp.platform.plants.interfaces.rest.resources;

import java.time.LocalDateTime;

public record UpdateProgressHistoryResource(
        LocalDateTime watered,
        LocalDateTime fertilized,
        LocalDateTime pests
) {
}

