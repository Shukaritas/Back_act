package com.agroapp.platform.iam.interfaces.rest.transform;

import com.agroapp.platform.iam.domain.model.commands.UpdateUserProfileCommand;
import com.agroapp.platform.iam.interfaces.rest.resources.UpdateUserProfileResource;

public class UpdateUserProfileCommandFromResourceAssembler {
    public static UpdateUserProfileCommand toCommandFromResource(Long userId, UpdateUserProfileResource resource) {
        return new UpdateUserProfileCommand(
                userId,
                resource.userName(),
                resource.email(),
                resource.phoneNumber()
        );
    }
}

