package com.agroapp.platform.plants.domain.services;

import com.agroapp.platform.plants.domain.model.aggregates.Task;
import com.agroapp.platform.plants.domain.model.commands.*;
import com.agroapp.platform.plants.domain.model.commands.CreateTaskCommand;
import com.agroapp.platform.plants.domain.model.commands.DeleteTaskCommand;
import com.agroapp.platform.plants.domain.model.commands.UpdateTaskCommand;

import java.util.Optional;

public interface TaskCommandService {
    Optional<Task> handle(CreateTaskCommand command);
    Optional<Task> handle(UpdateTaskCommand command);
    void handle(DeleteTaskCommand command);
}

