package com.agroapp.platform.iam.interfaces.rest.transform;

import com.agroapp.platform.iam.domain.model.commands.UpdateUserPasswordCommand;
import com.agroapp.platform.iam.interfaces.rest.resources.UpdateUserPasswordResource;

public class UpdateUserPasswordCommandFromResourceAssembler {
    public static UpdateUserPasswordCommand toCommandFromResource(Long userId, UpdateUserPasswordResource resource) {
        return new UpdateUserPasswordCommand(
                userId,
                resource.currentPassword(),
                resource.newPassword()
        );
    }
}

