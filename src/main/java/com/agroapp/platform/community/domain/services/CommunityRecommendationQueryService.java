package com.agroapp.platform.community.domain.services;

import com.agroapp.platform.community.domain.model.aggregates.CommunityRecommendation;
import com.agroapp.platform.community.domain.model.queries.*;
import com.agroapp.platform.community.domain.model.queries.GetAllCommunityRecommendationsQuery;
import com.agroapp.platform.community.domain.model.queries.GetCommunityRecommendationByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CommunityRecommendationQueryService {
    List<CommunityRecommendation> handle(GetAllCommunityRecommendationsQuery query);
    Optional<CommunityRecommendation> handle(GetCommunityRecommendationByIdQuery query);
}

