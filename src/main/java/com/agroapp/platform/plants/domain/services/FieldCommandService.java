package com.agroapp.platform.plants.domain.services;

import com.agroapp.platform.plants.domain.model.aggregates.Field;
import com.agroapp.platform.plants.domain.model.commands.*;
import com.agroapp.platform.plants.domain.model.commands.CreateFieldCommand;

import java.util.Optional;

public interface FieldCommandService {
    Optional<Field> handle(CreateFieldCommand command);
}


