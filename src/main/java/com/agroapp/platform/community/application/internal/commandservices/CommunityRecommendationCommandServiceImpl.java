package com.agroapp.platform.community.application.internal.commandservices;

import com.agroapp.platform.community.application.internal.outboundservices.acl.ExternalUserService;
import com.agroapp.platform.community.domain.model.aggregates.CommunityRecommendation;
import com.agroapp.platform.community.domain.model.commands.CreateCommunityRecommendationCommand;
import com.agroapp.platform.community.domain.model.commands.UpdateCommunityRecommendationCommand;
import com.agroapp.platform.community.domain.services.CommunityRecommendationCommandService;
import com.agroapp.platform.community.infrastructure.persistence.jpa.repositories.CommunityRecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of CommunityRecommendationCommandService.
 * Handles write operations for CommunityRecommendation aggregate.
 * Uses ACL (ExternalUserService) to communicate with IAM context.
 */
@Service
public class CommunityRecommendationCommandServiceImpl implements CommunityRecommendationCommandService {

    private final CommunityRecommendationRepository communityRecommendationRepository;
    private final ExternalUserService externalUserService;

    public CommunityRecommendationCommandServiceImpl(
            CommunityRecommendationRepository communityRecommendationRepository,
            ExternalUserService externalUserService) {
        this.communityRecommendationRepository = communityRecommendationRepository;
        this.externalUserService = externalUserService;
    }

    /**
     * Creates a new CommunityRecommendation.
     * userName is obtained from the IAM context via ACL using the provided userId.
     * id and commentDate are auto-generated.
     *
     * @param command CreateCommunityRecommendationCommand with userId and comment
     * @return Created CommunityRecommendation
     * @throws RuntimeException if user is not found
     */
    @Override
    public Optional<CommunityRecommendation> handle(CreateCommunityRecommendationCommand command) {
        // Validate that userId is provided
        if (command.userId() == null) {
            throw new IllegalArgumentException("User ID is required to create a recommendation");
        }

        // Get userName from IAM context via ACL
        String userName = externalUserService.getUserNameById(command.userId());

        // Validate that user exists
        if ("Anonymous".equals(userName)) {
            throw new RuntimeException("User with ID " + command.userId() + " not found");
        }

        CommunityRecommendation recommendation = new CommunityRecommendation(
                command.userId(),
                userName,
                command.comment()
        );

        CommunityRecommendation savedRecommendation = communityRecommendationRepository.save(recommendation);
        return Optional.of(savedRecommendation);
    }

    @Override
    public Optional<CommunityRecommendation> handle(UpdateCommunityRecommendationCommand command) {
        Optional<CommunityRecommendation> recommendationOptional = communityRecommendationRepository.findById(command.recommendationId());

        if (recommendationOptional.isEmpty()) {
            throw new RuntimeException("CommunityRecommendation not found");
        }

        CommunityRecommendation recommendation = recommendationOptional.get();
        recommendation.update(command.userName(), command.comment());
        CommunityRecommendation updatedRecommendation = communityRecommendationRepository.save(recommendation);
        return Optional.of(updatedRecommendation);
    }
}

