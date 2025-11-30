package com.agroapp.platform.iam.interfaces.rest.transform;

import com.agroapp.platform.iam.domain.model.aggregates.User;
import com.agroapp.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                token
        );
    }
}

