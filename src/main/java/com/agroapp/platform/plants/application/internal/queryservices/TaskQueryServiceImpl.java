package com.agroapp.platform.plants.application.internal.queryservices;

import com.agroapp.platform.plants.domain.model.aggregates.Task;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllTasksQuery;
import com.agroapp.platform.plants.domain.model.queries.GetTaskByIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetTasksByFieldIdQuery;
import com.agroapp.platform.plants.domain.services.TaskQueryService;
import com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskQueryServiceImpl implements TaskQueryService {

    private final TaskRepository taskRepository;

    public TaskQueryServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> handle(GetAllTasksQuery query) {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> handle(GetTaskByIdQuery query) {
        return taskRepository.findById(query.taskId());
    }

    @Override
    public List<Task> handle(GetTasksByFieldIdQuery query) {
        return taskRepository.findByFieldId(query.fieldId());
    }
}

