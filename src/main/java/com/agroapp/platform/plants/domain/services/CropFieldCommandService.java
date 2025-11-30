package com.agroapp.platform.plants.domain.services;


import com.agroapp.platform.plants.domain.model.commands.CreateCropFieldCommand;
import com.agroapp.platform.plants.domain.model.commands.DeleteCropFieldCommand;
import com.agroapp.platform.plants.domain.model.commands.UpdateCropFieldCommand;
import com.agroapp.platform.plants.domain.model.entities.CropField;

import java.util.Optional;

public interface CropFieldCommandService {
    Optional<CropField> handle(CreateCropFieldCommand command);
    Optional<CropField> handle(UpdateCropFieldCommand command);
    void handle(DeleteCropFieldCommand command);
}

