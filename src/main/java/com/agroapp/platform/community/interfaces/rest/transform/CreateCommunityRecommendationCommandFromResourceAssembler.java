package com.agroapp.platform.community.interfaces.rest.transform;

import com.agroapp.platform.community.domain.model.commands.CreateCommunityRecommendationCommand;
import com.agroapp.platform.community.interfaces.rest.resources.CreateCommunityRecommendationResource;

/**
 * Assembler to transform CreateCommunityRecommendationResource to CreateCommunityRecommendationCommand.
 * Pure transformation, no business logic.
 */
public class CreateCommunityRecommendationCommandFromResourceAssembler {

    /**
     * Transforms a CreateCommunityRecommendationResource into a CreateCommunityRecommendationCommand.
     *
     * @param resource The incoming REST resource with userId and comment
     * @return CreateCommunityRecommendationCommand to be processed by the domain layer
     */
    public static CreateCommunityRecommendationCommand toCommandFromResource(CreateCommunityRecommendationResource resource) {
        return new CreateCommunityRecommendationCommand(
                resource.userId(),
                resource.comment()
        );
    }
}

