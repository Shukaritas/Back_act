package com.agroapp.platform.community.interfaces.rest.resources;

/**
 * Resource for creating a CommunityRecommendation.
 * Requires userId (of the logged-in user) and comment content.
 * userName is resolved from userId via ACL to IAM context.
 * id and commentDate are auto-generated.
 */
public record CreateCommunityRecommendationResource(
        Long userId,
        String comment
) {
}

