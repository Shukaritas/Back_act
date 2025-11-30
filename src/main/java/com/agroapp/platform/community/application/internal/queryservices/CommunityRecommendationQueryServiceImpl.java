package com.agroapp.platform.community.application.internal.queryservices;

import com.agroapp.platform.community.domain.model.aggregates.CommunityRecommendation;
import com.agroapp.platform.community.domain.model.queries.*;
import com.agroapp.platform.community.domain.model.queries.GetAllCommunityRecommendationsQuery;
import com.agroapp.platform.community.domain.model.queries.GetCommunityRecommendationByIdQuery;
import com.agroapp.platform.community.domain.services.CommunityRecommendationQueryService;
import com.agroapp.platform.community.infrastructure.persistence.jpa.repositories.CommunityRecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityRecommendationQueryServiceImpl implements CommunityRecommendationQueryService {

    private final CommunityRecommendationRepository communityRecommendationRepository;

    public CommunityRecommendationQueryServiceImpl(CommunityRecommendationRepository communityRecommendationRepository) {
        this.communityRecommendationRepository = communityRecommendationRepository;
    }

    @Override
    public List<CommunityRecommendation> handle(GetAllCommunityRecommendationsQuery query) {
        return communityRecommendationRepository.findAll();
    }

    @Override
    public Optional<CommunityRecommendation> handle(GetCommunityRecommendationByIdQuery query) {
        return communityRecommendationRepository.findById(query.recommendationId());
    }
}

