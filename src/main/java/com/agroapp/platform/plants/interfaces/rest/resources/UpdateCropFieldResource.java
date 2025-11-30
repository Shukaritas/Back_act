package com.agroapp.platform.plants.interfaces.rest.resources;

import com.agroapp.platform.plants.domain.model.valueobjects.CropFieldStatus;

public record UpdateCropFieldResource(
        Long cropFieldId,
        String crop,
        CropFieldStatus status
) {
}

