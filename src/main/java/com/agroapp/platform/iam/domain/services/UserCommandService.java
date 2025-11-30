package com.agroapp.platform.iam.domain.services;

import com.agroapp.platform.iam.domain.model.aggregates.User;
import com.agroapp.platform.iam.domain.model.commands.*;
import com.agroapp.platform.iam.domain.model.commands.*;

import java.util.Optional;

public interface UserCommandService {
    Optional<User> handle(SignUpCommand command);
    Optional<String> handle(SignInCommand command);
    Optional<User> handle(UpdateUserProfileCommand command);
    Optional<User> handle(UpdateUserPasswordCommand command);
    void handle(DeleteUserCommand command);
}

