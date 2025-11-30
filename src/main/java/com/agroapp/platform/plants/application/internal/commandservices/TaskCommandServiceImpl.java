package com.agroapp.platform.plants.application.internal.commandservices;

import com.agroapp.platform.plants.domain.model.aggregates.Task;
import com.agroapp.platform.plants.domain.model.commands.*;
import com.agroapp.platform.plants.domain.model.commands.CreateTaskCommand;
import com.agroapp.platform.plants.domain.model.commands.DeleteTaskCommand;
import com.agroapp.platform.plants.domain.model.commands.UpdateTaskCommand;
import com.agroapp.platform.plants.domain.services.TaskCommandService;
import com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskCommandServiceImpl implements TaskCommandService {

    private final TaskRepository taskRepository;

    public TaskCommandServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> handle(CreateTaskCommand command) {
        Task task = new Task(
                command.fieldId(),
                command.description(),
                command.dueDate()
        );

        Task savedTask = taskRepository.save(task);
        return Optional.of(savedTask);
    }

    @Override
    public Optional<Task> handle(UpdateTaskCommand command) {
        Optional<Task> taskOptional = taskRepository.findById(command.taskId());

        if (taskOptional.isEmpty()) {
            throw new RuntimeException("Task not found");
        }

        Task task = taskOptional.get();
        task.update(command.fieldId(), command.description(), command.dueDate());
        Task updatedTask = taskRepository.save(task);
        return Optional.of(updatedTask);
    }

    @Override
    public void handle(DeleteTaskCommand command) {
        if (!taskRepository.existsById(command.taskId())) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(command.taskId());
    }
}

