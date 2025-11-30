package com.agroapp.platform.community.interfaces.rest.transform;

import com.agroapp.platform.community.domain.model.aggregates.CommunityRecommendation;
import com.agroapp.platform.community.interfaces.rest.resources.CommunityRecommendationResource;

public class CommunityRecommendationResourceFromEntityAssembler {
    public static CommunityRecommendationResource toResourceFromEntity(CommunityRecommendation recommendation) {
        return new CommunityRecommendationResource(
                recommendation.getId(),
                recommendation.getUserName(),
                recommendation.getCommentDate(),
                recommendation.getComment()
        );
    }
}

