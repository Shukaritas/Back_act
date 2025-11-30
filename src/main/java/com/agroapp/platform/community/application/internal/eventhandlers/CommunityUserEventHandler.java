package com.agroapp.platform.community.application.internal.eventhandlers;

import com.agroapp.platform.community.infrastructure.persistence.jpa.repositories.CommunityRecommendationRepository;
import com.agroapp.platform.iam.domain.model.events.UserProfileUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Event handler for user-related events from the IAM bounded context.
 * Listens to user profile updates and synchronizes the userName in community recommendations.
 */
@Component
public class CommunityUserEventHandler {

    private final CommunityRecommendationRepository communityRecommendationRepository;

    public CommunityUserEventHandler(CommunityRecommendationRepository communityRecommendationRepository) {
        this.communityRecommendationRepository = communityRecommendationRepository;
    }

    /**
     * Handles UserProfileUpdatedEvent from IAM context.
     * Updates the userName in all recommendations created by the user.
     *
     * @param event the UserProfileUpdatedEvent containing userId and new userName
     */
    @EventListener
    @Transactional
    public void on(UserProfileUpdatedEvent event) {
        // Update all recommendations from this user with the new userName
        communityRecommendationRepository.updateUserNameByUserId(event.getUserId(), event.getNewUserName());
    }
}

