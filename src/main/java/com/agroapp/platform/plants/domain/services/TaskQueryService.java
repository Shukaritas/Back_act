package com.agroapp.platform.plants.domain.services;

import com.agroapp.platform.plants.domain.model.aggregates.Task;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllTasksQuery;
import com.agroapp.platform.plants.domain.model.queries.GetTaskByIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetTasksByFieldIdQuery;

import java.util.List;
import java.util.Optional;

public interface TaskQueryService {
    List<Task> handle(GetAllTasksQuery query);
    Optional<Task> handle(GetTaskByIdQuery query);
    List<Task> handle(GetTasksByFieldIdQuery query);
}

