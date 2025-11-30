package com.agroapp.platform.community.interfaces.rest;

import com.agroapp.platform.community.domain.model.queries.*;
import com.agroapp.platform.community.domain.services.*;
import com.agroapp.platform.community.interfaces.rest.resources.*;
import com.agroapp.platform.community.interfaces.rest.transform.*;
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
 * REST Controller for CommunityRecommendation aggregate.
 * Handles HTTP requests related to community recommendations.
 * Follows hexagonal architecture: delegates to services and uses assemblers for transformations.
 */
@RestController
@RequestMapping("/api/v1/community-recommendations")
@Tag(name = "CommunityRecommendation", description = "Community Recommendation Endpoints")
public class CommunityRecommendationController {

    private final CommunityRecommendationCommandService communityRecommendationCommandService;
    private final CommunityRecommendationQueryService communityRecommendationQueryService;

    public CommunityRecommendationController(CommunityRecommendationCommandService communityRecommendationCommandService,
                                             CommunityRecommendationQueryService communityRecommendationQueryService) {
        this.communityRecommendationCommandService = communityRecommendationCommandService;
        this.communityRecommendationQueryService = communityRecommendationQueryService;
    }

    /**
     * Creates a new CommunityRecommendation.
     * Requires userId (of the logged-in user) and comment content.
     * userName is automatically resolved from userId via ACL to IAM context.
     * commentDate is auto-generated with current timestamp.
     * POST /api/v1/CommunityRecommendation
     */
    @Operation(
            summary = "Create a new community recommendation",
            description = "Creates a recommendation with the authenticated user as author. " +
                    "Requires userId (must exist in the system) and comment content. " +
                    "The userName is automatically retrieved from the user profile via ACL. " +
                    "The commentDate is auto-generated with current timestamp."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Recommendation successfully created",
                    content = @Content(schema = @Schema(implementation = CommunityRecommendationResource.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input - userId is required"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "User not found - The provided userId does not exist in the system"
            )
    })
    @PostMapping
    public ResponseEntity<CommunityRecommendationResource> createCommunityRecommendation(@RequestBody CreateCommunityRecommendationResource resource) {
        // Transform Resource to Command using Assembler
        var command = CreateCommunityRecommendationCommandFromResourceAssembler.toCommandFromResource(resource);

        // Execute command through service (uses ACL to get userName)
        var recommendation = communityRecommendationCommandService.handle(command);
        if (recommendation.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Transform Entity to Resource using Assembler
        var recommendationResource = CommunityRecommendationResourceFromEntityAssembler.toResourceFromEntity(recommendation.get());
        return new ResponseEntity<>(recommendationResource, HttpStatus.CREATED);
    }

    /**
     * Gets a CommunityRecommendation by its ID.
     * GET /api/v1/CommunityRecommendation/{recommendationId}
     */
    @GetMapping("/{recommendationId}")
    public ResponseEntity<CommunityRecommendationResource> getCommunityRecommendationById(@PathVariable Long recommendationId) {
        var query = new GetCommunityRecommendationByIdQuery(recommendationId);
        var recommendation = communityRecommendationQueryService.handle(query);

        if (recommendation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var recommendationResource = CommunityRecommendationResourceFromEntityAssembler.toResourceFromEntity(recommendation.get());
        return ResponseEntity.ok(recommendationResource);
    }

    /**
     * Gets all CommunityRecommendations.
     * GET /api/v1/community-recommendations
     */
    @GetMapping
    public ResponseEntity<List<CommunityRecommendationResource>> getAllCommunityRecommendations() {
        var query = new GetAllCommunityRecommendationsQuery();
        var recommendations = communityRecommendationQueryService.handle(query);

        var recommendationResources = recommendations.stream()
                .map(CommunityRecommendationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(recommendationResources);
    }

    /**
     * Updates a CommunityRecommendation.
     * PUT /api/v1/community-recommendations/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommunityRecommendationResource> updateCommunityRecommendation(@PathVariable Long id, @RequestBody UpdateCommunityRecommendationResource resource) {
        var command = UpdateCommunityRecommendationCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var recommendation = communityRecommendationCommandService.handle(command);

        if (recommendation.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var recommendationResource = CommunityRecommendationResourceFromEntityAssembler.toResourceFromEntity(recommendation.get());
        return ResponseEntity.ok(recommendationResource);
    }
}
