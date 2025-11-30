package com.agroapp.platform.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(
        Long id,
        String userName,
        String email,
        String token,
        boolean isLocationPublic
) {
}

