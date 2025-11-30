package com.agroapp.platform.community.domain.model.commands;

/**
 * Command to create a CommunityRecommendation.
 * Requires userId (of the authenticated user) and comment content.
 * userName is resolved from userId, commentDate is auto-generated.
 */
public record CreateCommunityRecommendationCommand(
        Long userId,
        String comment
) {
}

