package com.agroapp.platform.iam.application.internal.commandservices;

import com.agroapp.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.agroapp.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.agroapp.platform.iam.domain.model.aggregates.User;
import com.agroapp.platform.iam.domain.model.commands.*;
import com.agroapp.platform.iam.domain.model.commands.*;
import com.agroapp.platform.iam.domain.model.events.UserProfileUpdatedEvent;
import com.agroapp.platform.iam.domain.services.UserCommandService;
import com.agroapp.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService,
                                 TokenService tokenService, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByIdentificator(command.identificator())) {
            throw new RuntimeException("DNI (identificator) already exists");
        }

        // Validate raw password before hashing
        User.validateRawPassword(command.password());

        String hashedPassword = hashingService.encode(command.password());
        User user = new User(
                command.userName(),
                command.email(),
                hashedPassword,
                command.phoneNumber(),
                command.identificator(),
                command.location()
        );

        User savedUser = userRepository.save(user);
        return Optional.of(savedUser);
    }

    @Override
    public Optional<String> handle(SignInCommand command) {
        Optional<User> userOptional = userRepository.findByEmail(command.email());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOptional.get();
        if (!hashingService.matches(command.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = tokenService.generateToken(user.getEmail());
        return Optional.of(token);
    }

    @Override
    public Optional<User> handle(UpdateUserProfileCommand command) {
        Optional<User> userOptional = userRepository.findById(command.userId());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        user.updateProfile(command.userName(), command.email(), command.phoneNumber(), command.isLocationPublic());
        User updatedUser = userRepository.save(user);

        // Publish event to notify other bounded contexts about the user profile update
        eventPublisher.publishEvent(new UserProfileUpdatedEvent(this, updatedUser.getId(), updatedUser.getUserName()));

        return Optional.of(updatedUser);
    }

    @Override
    public Optional<User> handle(UpdateUserPasswordCommand command) {
        Optional<User> userOptional = userRepository.findById(command.userId());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        if (!hashingService.matches(command.currentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        String hashedPassword = hashingService.encode(command.newPassword());
        user.updatePassword(hashedPassword);
        User updatedUser = userRepository.save(user);
        return Optional.of(updatedUser);
    }

    @Override
    public void handle(DeleteUserCommand command) {
        if (!userRepository.existsById(command.userId())) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(command.userId());
    }
}

