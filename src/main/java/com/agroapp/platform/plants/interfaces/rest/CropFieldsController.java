package com.agroapp.platform.plants.interfaces.rest;

import com.agroapp.platform.plants.domain.model.commands.DeleteCropFieldCommand;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.services.*;
import com.agroapp.platform.plants.interfaces.rest.resources.*;
import com.agroapp.platform.plants.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for CropField entity.
 * Handles HTTP requests related to CropField management.
 * CropField has a 1:1 relationship with Field.
 * Follows hexagonal architecture: delegates to services and uses assemblers for transformations.
 */
@RestController
@RequestMapping("/api/v1/crop-fields")
@Tag(name = "CropFields", description = "CropField Management Endpoints")
public class CropFieldsController {

    private final CropFieldCommandService cropFieldCommandService;
    private final CropFieldQueryService cropFieldQueryService;

    public CropFieldsController(CropFieldCommandService cropFieldCommandService,
                                CropFieldQueryService cropFieldQueryService) {
        this.cropFieldCommandService = cropFieldCommandService;
        this.cropFieldQueryService = cropFieldQueryService;
    }

    /**
     * Creates a new CropField associated with an existing Field.
     * POST /api/v1/CropFields
     */
    @Operation(
            summary = "Create a new CropField",
            description = "Creates a new CropField associated with an existing Field (1:1 relationship). " +
                    "The 'status' field is an ENUM with allowed values: Healthy, Attention, Critical. " +
                    "These represent the health status of the crop in the field."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CropField successfully created",
                    content = @Content(schema = @Schema(implementation = CropFieldResource.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input - Check that fieldId exists and status is one of: Healthy, Attention, Critical"
            )
    })
    @PostMapping
    public ResponseEntity<CropFieldResource> createCropField(@RequestBody CreateCropFieldResource resource) {
        // Transform Resource to Command using Assembler
        var command = CreateCropFieldCommandFromResourceAssembler.toCommandFromResource(resource);

        // Execute command through service
        var cropField = cropFieldCommandService.handle(command);
        if (cropField.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Transform Entity to Resource using Assembler
        var cropFieldResource = CropFieldResourceFromEntityAssembler.toResourceFromEntity(cropField.get());
        return new ResponseEntity<>(cropFieldResource, HttpStatus.CREATED);
    }

    /**
     * Gets all CropFields.
     * GET /api/v1/crop-fields
     */
    @GetMapping
    public ResponseEntity<List<CropFieldResource>> getAllCropFields() {
        var query = new GetAllCropFieldsQuery();
        var cropFields = cropFieldQueryService.handle(query);

        var cropFieldResources = cropFields.stream()
                .map(CropFieldResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cropFieldResources);
    }

    /**
     * Gets a CropField by its ID.
     * GET /api/v1/CropFields/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CropFieldResource> getCropFieldById(@PathVariable Long id) {
        var query = new GetCropFieldByIdQuery(id);
        var cropField = cropFieldQueryService.handle(query);

        if (cropField.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var cropFieldResource = CropFieldResourceFromEntityAssembler.toResourceFromEntity(cropField.get());
        return ResponseEntity.ok(cropFieldResource);
    }

    /**
     * Updates a CropField's crop attribute and status.
     * PUT /api/v1/crop-fields/{id}
     */
    @Operation(
            summary = "Update a CropField",
            description = "Updates the crop name and/or status of an existing CropField. " +
                    "Status must be one of: Healthy, Attention, Critical."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "CropField successfully updated",
                    content = @Content(schema = @Schema(implementation = CropFieldResource.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - Check that status is one of: Healthy, Attention, Critical"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CropField not found"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CropFieldResource> updateCropField(@PathVariable Long id, @RequestBody UpdateCropFieldResource resource) {
        // Transform Resource to Command using Assembler (includes ID in the resource)
        var command = UpdateCropFieldCommandFromResourceAssembler.toCommandFromResource(
                new UpdateCropFieldResource(id, resource.crop(), resource.status())
        );

        // Execute command through service
        var cropField = cropFieldCommandService.handle(command);
        if (cropField.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Transform Entity to Resource using Assembler
        var cropFieldResource = CropFieldResourceFromEntityAssembler.toResourceFromEntity(cropField.get());
        return ResponseEntity.ok(cropFieldResource);
    }

    /**
     * Deletes a CropField by its ID.
     * DELETE /api/v1/crop-fields/{id}
     */
    @Operation(
            summary = "Delete a CropField",
            description = "Deletes an existing CropField by its ID. This removes the crop association from the field."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "CropField successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CropField not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCropField(@PathVariable Long id) {
        var command = new DeleteCropFieldCommand(id);
        cropFieldCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    /**
     * Gets a CropField by its associated Field ID (1:1 relationship).
     * GET /api/v1/crop-fields/field/{fieldId}
     */
    @GetMapping("/field/{fieldId}")
    public ResponseEntity<CropFieldResource> getCropFieldByFieldId(@PathVariable Long fieldId) {
        var query = new GetCropFieldByFieldIdQuery(fieldId);
        var cropField = cropFieldQueryService.handle(query);

        if (cropField.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var cropFieldResource = CropFieldResourceFromEntityAssembler.toResourceFromEntity(cropField.get());
        return ResponseEntity.ok(cropFieldResource);
    }
}
