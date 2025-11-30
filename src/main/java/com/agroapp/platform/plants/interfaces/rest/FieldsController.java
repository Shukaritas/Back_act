package com.agroapp.platform.plants.interfaces.rest;

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
 * REST Controller for Field aggregate.
 * Handles HTTP requests related to Field management.
 * Follows hexagonal architecture: delegates to services and uses assemblers for transformations.
 */
@RestController
@RequestMapping("/api/v1/fields")
@Tag(name = "Fields", description = "Field Management Endpoints")
public class FieldsController {

    private final FieldCommandService fieldCommandService;
    private final FieldQueryService fieldQueryService;
    private final ProgressHistoryQueryService progressHistoryQueryService;
    private final CropFieldQueryService cropFieldQueryService;
    private final TaskQueryService taskQueryService;

    public FieldsController(FieldCommandService fieldCommandService,
                            FieldQueryService fieldQueryService,
                            ProgressHistoryQueryService progressHistoryQueryService,
                            CropFieldQueryService cropFieldQueryService,
                            TaskQueryService taskQueryService) {
        this.fieldCommandService = fieldCommandService;
        this.fieldQueryService = fieldQueryService;
        this.progressHistoryQueryService = progressHistoryQueryService;
        this.cropFieldQueryService = cropFieldQueryService;
        this.taskQueryService = taskQueryService;
    }

    /**
     * Creates a new Field and automatically creates its associated ProgressHistory.
     * POST /api/v1/fields
     */
    @PostMapping
    public ResponseEntity<FieldResource> createField(@RequestBody CreateFieldResource resource) {
        // Transform Resource to Command using Assembler
        var command = CreateFieldCommandFromResourceAssembler.toCommandFromResource(resource);

        // Execute command through service
        var field = fieldCommandService.handle(command);
        if (field.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Resolve related data for the resource
        var fieldEntity = field.get();
        var progressHistory = progressHistoryQueryService.handle(new GetProgressHistoryByFieldIdQuery(fieldEntity.getId()));
        var cropField = cropFieldQueryService.handle(new GetCropFieldByFieldIdQuery(fieldEntity.getId()));
        var tasks = taskQueryService.handle(new GetTasksByFieldIdQuery(fieldEntity.getId()));

        // Transform Entity to Resource using Assembler
        var fieldResource = FieldResourceFromEntityAssembler.toResourceFromEntity(
                fieldEntity,
                progressHistory.map(ph -> ph.getId()).orElse(null),
                cropField.map(cf -> cf.getId()).orElse(null),
                tasks.stream().map(task -> task.getId()).collect(Collectors.toList())
        );

        return new ResponseEntity<>(fieldResource, HttpStatus.CREATED);
    }

    /**
     * Gets all Fields by user ID.
     * GET /api/v1/Fields/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FieldResource>> getFieldsByUserId(@PathVariable Long userId) {
        var query = new GetFieldsByUserIdQuery(userId);
        var fields = fieldQueryService.handle(query);

        var fieldResources = fields.stream()
                .map(field -> {
                    // Resolve related data for each field
                    var progressHistory = progressHistoryQueryService.handle(new GetProgressHistoryByFieldIdQuery(field.getId()));
                    var cropField = cropFieldQueryService.handle(new GetCropFieldByFieldIdQuery(field.getId()));
                    var tasks = taskQueryService.handle(new GetTasksByFieldIdQuery(field.getId()));

                    // Transform using Assembler
                    return FieldResourceFromEntityAssembler.toResourceFromEntity(
                            field,
                            progressHistory.map(ph -> ph.getId()).orElse(null),
                            cropField.map(cf -> cf.getId()).orElse(null),
                            tasks.stream().map(task -> task.getId()).collect(Collectors.toList())
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(fieldResources);
    }

    /**
     * Gets a Field by its ID.
     * GET /api/v1/fields/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldResource> getFieldById(@PathVariable Long id) {
        var query = new GetFieldByIdQuery(id);
        var field = fieldQueryService.handle(query);

        if (field.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Resolve related data
        var fieldEntity = field.get();
        var progressHistory = progressHistoryQueryService.handle(new GetProgressHistoryByFieldIdQuery(fieldEntity.getId()));
        var cropField = cropFieldQueryService.handle(new GetCropFieldByFieldIdQuery(fieldEntity.getId()));
        var tasks = taskQueryService.handle(new GetTasksByFieldIdQuery(fieldEntity.getId()));

        // Transform using Assembler
        var fieldResource = FieldResourceFromEntityAssembler.toResourceFromEntity(
                fieldEntity,
                progressHistory.map(ph -> ph.getId()).orElse(null),
                cropField.map(cf -> cf.getId()).orElse(null),
                tasks.stream().map(task -> task.getId()).collect(Collectors.toList())
        );

        return ResponseEntity.ok(fieldResource);
    }
}

