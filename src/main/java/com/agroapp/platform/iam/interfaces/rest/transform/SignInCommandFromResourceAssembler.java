package com.agroapp.platform.iam.interfaces.rest.transform;

import com.agroapp.platform.iam.domain.model.commands.SignInCommand;
import com.agroapp.platform.iam.interfaces.rest.resources.SignInUserResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInUserResource resource) {
        return new SignInCommand(
                resource.email(),
                resource.password()
        );
    }
}

