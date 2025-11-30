package com.agroapp.platform.plants.domain.model.commands;


import com.agroapp.platform.plants.domain.model.valueobjects.CropFieldStatus;

public record UpdateCropFieldCommand(
        Long cropFieldId,
        String crop,
        CropFieldStatus status
) {
}

