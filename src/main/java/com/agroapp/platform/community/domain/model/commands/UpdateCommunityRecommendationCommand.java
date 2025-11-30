package com.agroapp.platform.community.domain.model.commands;

public record UpdateCommunityRecommendationCommand(
        Long recommendationId,
        String userName,
        String comment
) {
}

