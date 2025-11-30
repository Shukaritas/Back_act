package com.agroapp.platform.community.interfaces.rest.transform;

import com.agroapp.platform.community.domain.model.commands.UpdateCommunityRecommendationCommand;
import com.agroapp.platform.community.interfaces.rest.resources.UpdateCommunityRecommendationResource;

public class UpdateCommunityRecommendationCommandFromResourceAssembler {
    public static UpdateCommunityRecommendationCommand toCommandFromResource(Long recommendationId, UpdateCommunityRecommendationResource resource) {
        return new UpdateCommunityRecommendationCommand(
                recommendationId,
                resource.userName(),
                resource.comment()
        );
    }
}

