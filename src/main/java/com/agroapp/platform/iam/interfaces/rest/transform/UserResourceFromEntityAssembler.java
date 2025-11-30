package com.agroapp.platform.iam.interfaces.rest.transform;

import com.agroapp.platform.iam.domain.model.aggregates.User;
import com.agroapp.platform.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getIdentificator(),
                user.getLocation(),
                user.isLocationPublic()
        );
    }
}

