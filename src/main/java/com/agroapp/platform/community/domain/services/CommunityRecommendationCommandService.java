package com.agroapp.platform.community.domain.services;

import com.agroapp.platform.community.domain.model.aggregates.CommunityRecommendation;
import com.agroapp.platform.community.domain.model.commands.*;

import java.util.Optional;

/**
 * Command Service interface for CommunityRecommendation aggregate.
 * Handles write operations (Commands).
 */
public interface CommunityRecommendationCommandService {
    Optional<CommunityRecommendation> handle(CreateCommunityRecommendationCommand command);
    Optional<CommunityRecommendation> handle(UpdateCommunityRecommendationCommand command);
}

