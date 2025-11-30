package com.agroapp.platform.plants.interfaces.rest;

import com.agroapp.platform.plants.domain.model.commands.DeleteTaskCommand;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.services.*;
import com.agroapp.platform.plants.interfaces.rest.resources.*;
import com.agroapp.platform.plants.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Task aggregate.
 * Handles HTTP requests related to Task management.
 * Follows hexagonal architecture: delegates to services and uses assemblers for transformations.
 */
@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Task Management Endpoints")
public class TasksController {

    private final TaskCommandService taskCommandService;
    private final TaskQueryService taskQueryService;

    public TasksController(TaskCommandService taskCommandService,
                           TaskQueryService taskQueryService) {
        this.taskCommandService = taskCommandService;
        this.taskQueryService = taskQueryService;
    }

    /**
     * Gets all tasks.
     * GET /api/Tasks
     */
    @GetMapping
    public ResponseEntity<List<TaskResource>> getAllTasks() {
        var query = new GetAllTasksQuery();
        var tasks = taskQueryService.handle(query);

        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(taskResources);
    }

    /**
     * Creates a new task.
     * POST /api/Tasks
     */
    @PostMapping
    public ResponseEntity<TaskResource> createTask(@RequestBody CreateTaskResource resource) {
        // Transform Resource to Command using Assembler
        var command = CreateTaskCommandFromResourceAssembler.toCommandFromResource(resource);

        // Execute command through service
        var task = taskCommandService.handle(command);
        if (task.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Transform Entity to Resource using Assembler
        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());
        return new ResponseEntity<>(taskResource, HttpStatus.CREATED);
    }

    /**
     * Gets a task by its ID.
     * GET /api/Tasks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResource> getTaskById(@PathVariable Long id) {
        var query = new GetTaskByIdQuery(id);
        var task = taskQueryService.handle(query);

        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());
        return ResponseEntity.ok(taskResource);
    }

    /**
     * Updates an existing task.
     * PUT /api/Tasks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResource> updateTask(@PathVariable Long id, @RequestBody EditTaskResource resource) {
        // Transform Resource to Command using Assembler
        var command = UpdateTaskCommandFromResourceAssembler.toCommandFromResource(id, resource);

        // Execute command through service
        var task = taskCommandService.handle(command);
        if (task.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Transform Entity to Resource using Assembler
        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());
        return ResponseEntity.ok(taskResource);
    }

    /**
     * Deletes a task by its ID.
     * DELETE /api/Tasks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        var command = new DeleteTaskCommand(id);
        taskCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    /**
     * Gets all Tasks for a specific Field.
     * GET /api/v1/tasks/field/{fieldId}
     */
    @GetMapping("/field/{fieldId}")
    public ResponseEntity<List<TaskResource>> getTasksByFieldId(@PathVariable Long fieldId) {
        var query = new GetTasksByFieldIdQuery(fieldId);
        var tasks = taskQueryService.handle(query);

        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(taskResources);
    }
}

