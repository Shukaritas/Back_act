package com.agroapp.platform.plants.domain.services;

import com.agroapp.platform.plants.domain.model.commands.CreateProgressHistoryCommand;
import com.agroapp.platform.plants.domain.model.commands.UpdateProgressHistoryCommand;
import com.agroapp.platform.plants.domain.model.entities.ProgressHistory;
import com.agroapp.platform.plants.domain.model.commands.*;

import java.util.Optional;

public interface ProgressHistoryCommandService {
    Optional<ProgressHistory> handle(CreateProgressHistoryCommand command);
    Optional<ProgressHistory> handle(UpdateProgressHistoryCommand command);
}

