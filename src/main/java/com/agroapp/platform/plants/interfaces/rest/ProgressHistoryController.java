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
 * REST Controller for ProgressHistory entity.
 * Handles HTTP requests related to ProgressHistory management.
 * ProgressHistory has a 1:1 relationship with Field and is created automatically when a Field is created.
 * Follows hexagonal architecture: delegates to services and uses assemblers for transformations.
 */
@RestController
@RequestMapping("/api/v1/progress")
@Tag(name = "ProgressHistory", description = "ProgressHistory Management Endpoints")
public class ProgressHistoryController {

    private final ProgressHistoryCommandService progressHistoryCommandService;
    private final ProgressHistoryQueryService progressHistoryQueryService;

    public ProgressHistoryController(ProgressHistoryCommandService progressHistoryCommandService,
                                     ProgressHistoryQueryService progressHistoryQueryService) {
        this.progressHistoryCommandService = progressHistoryCommandService;
        this.progressHistoryQueryService = progressHistoryQueryService;
    }

    /**
     * Gets all ProgressHistories.
     * GET /api/v1/progress
     */
    @GetMapping
    public ResponseEntity<List<ProgressHistoryResource>> getAllProgressHistories() {
        var query = new GetAllProgressHistoriesQuery();
        var progressHistories = progressHistoryQueryService.handle(query);

        var progressHistoryResources = progressHistories.stream()
                .map(ProgressHistoryResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(progressHistoryResources);
    }

    /**
     * Creates a new ProgressHistory (manual creation, typically created automatically with Field).
     * POST /api/v1/progress
     */
    @PostMapping
    public ResponseEntity<ProgressHistoryResource> createProgressHistory(@RequestBody CreateProgressHistoryResource resource) {
        // Transform Resource to Command using Assembler
        var command = CreateProgressHistoryCommandFromResourceAssembler.toCommandFromResource(resource);

        // Execute command through service
        var progressHistory = progressHistoryCommandService.handle(command);
        if (progressHistory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Transform Entity to Resource using Assembler
        var progressHistoryResource = ProgressHistoryResourceFromEntityAssembler.toResourceFromEntity(progressHistory.get());
        return new ResponseEntity<>(progressHistoryResource, HttpStatus.CREATED);
    }

    /**
     * Gets a ProgressHistory by its ID.
     * GET /api/v1/progress/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgressHistoryResource> getProgressHistoryById(@PathVariable Long id) {
        var query = new GetProgressHistoryByIdQuery(id);
        var progressHistory = progressHistoryQueryService.handle(query);

        if (progressHistory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var progressHistoryResource = ProgressHistoryResourceFromEntityAssembler.toResourceFromEntity(progressHistory.get());
        return ResponseEntity.ok(progressHistoryResource);
    }

    /**
     * Updates a ProgressHistory (watered, fertilized, pests dates).
     * PUT /api/v1/progress/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProgressHistoryResource> updateProgressHistory(@PathVariable Long id, @RequestBody UpdateProgressHistoryResource resource) {
        // Transform Resource to Command using Assembler
        var command = UpdateProgressHistoryCommandFromResourceAssembler.toCommandFromResource(id, resource);

        // Execute command through service
        var progressHistory = progressHistoryCommandService.handle(command);
        if (progressHistory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Transform Entity to Resource using Assembler
        var progressHistoryResource = ProgressHistoryResourceFromEntityAssembler.toResourceFromEntity(progressHistory.get());
        return ResponseEntity.ok(progressHistoryResource);
    }
}

