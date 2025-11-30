package com.agroapp.platform.community.application.internal.outboundservices.acl;

import com.agroapp.platform.iam.domain.model.aggregates.User;
import com.agroapp.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Anti-Corruption Layer (ACL) service to communicate with IAM context.
 * Provides user information to Community context without creating direct dependencies.
 * Follows DDD principle: bounded contexts should not directly access each other's repositories.
 */
@Service
public class ExternalUserService {

    private final UserRepository userRepository;

    public ExternalUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets a user by their ID.
     * Used to retrieve user information for community recommendations.
     *
     * @param userId The user ID
     * @return Optional containing the User if found
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Gets a user by their email.
     * Used for authentication context lookups.
     *
     * @param email The user email
     * @return Optional containing the User if found
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Gets the username (full name) by user ID.
     * Convenience method for quick lookups.
     *
     * @param userId The user ID
     * @return The user's full name, or "Anonymous" if not found
     */
    public String getUserNameById(Long userId) {
        return userRepository.findById(userId)
                .map(User::getUserName)
                .orElse("Anonymous");
    }
}

