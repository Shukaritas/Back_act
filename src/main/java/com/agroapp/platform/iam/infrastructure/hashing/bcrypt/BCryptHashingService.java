package com.agroapp.platform.iam.infrastructure.hashing.bcrypt;

import com.agroapp.platform.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * BCrypt implementation of HashingService.
 * Uses Spring Security's PasswordEncoder for secure password hashing.
 */
@Service
public class BCryptHashingService implements HashingService {
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor with dependency injection.
     * Uses the PasswordEncoder bean configured in WebSecurityConfiguration.
     *
     * @param passwordEncoder Spring Security PasswordEncoder
     */
    public BCryptHashingService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

