package com.agroapp.platform.plants.domain.model.commands;

import com.agroapp.platform.plants.domain.model.valueobjects.CropFieldStatus;

import java.time.LocalDateTime;

public record CreateCropFieldCommand(
        Long fieldId,
        String crop,
        String soilType,
        String sunlight,
        String watering,
        LocalDateTime plantingDate,
        LocalDateTime harvestDate,
        CropFieldStatus status
) {
}

