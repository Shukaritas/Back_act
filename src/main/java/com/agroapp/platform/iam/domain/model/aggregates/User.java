package com.agroapp.platform.iam.domain.model.aggregates;

import com.agroapp.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * User Aggregate Root
 * Represents a user in the IAM bounded context.
 * Domain-driven design: encapsulates user management business logic with validations.
 */
@Entity
@Getter
public class User extends AuditableAbstractAggregateRoot<User> {

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String identificator;

    @Column
    private String location;

    /**
     * Default constructor required by JPA.
     */
    public User() {
    }

    /**
     * Creates a new User aggregate with business validations.
     *
     * @param userName User's full name (required)
     * @param email User's email (required, unique)
     * @param password User's password (required, min 5 characters - will be hashed)
     * @param phoneNumber User's phone with country prefix (required, format: +XX...)
     * @param identificator User's DNI (required, exactly 8 digits)
     * @param location User's geographical location (optional)
     * @throws IllegalArgumentException if any validation fails
     */
    public User(String userName, String email, String password, String phoneNumber, String identificator, String location) {
        // Validate userName
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }

        // Validate email
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email format is invalid");
        }

        // Validate password (minimum 5 characters)
        if (password == null || password.length() < 5) {
            throw new IllegalArgumentException("Password must be at least 5 characters long");
        }

        // Validate phone number (must start with +)
        if (phoneNumber == null || !phoneNumber.startsWith("+")) {
            throw new IllegalArgumentException("Phone number must include country prefix (e.g., +51987654321)");
        }
        if (phoneNumber.length() < 10) {
            throw new IllegalArgumentException("Phone number is too short (min 10 characters including prefix)");
        }

        // Validate identificator (DNI - exactly 8 digits)
        if (identificator == null || !identificator.matches("^\\d{8}$")) {
            throw new IllegalArgumentException("DNI must be exactly 8 digits (e.g., 12345678)");
        }

        this.userName = userName.trim();
        this.email = email.trim().toLowerCase();
        this.password = password;
        this.phoneNumber = phoneNumber.trim();
        this.identificator = identificator.trim();
        this.location = location;
    }

    /**
     * Updates user profile information.
     * Business logic method with semantic naming.
     *
     * @param userName New user name
     * @param email New email
     * @param phoneNumber New phone number
     * @return The updated User instance (fluent interface)
     */
    public User updateProfile(String userName, String email, String phoneNumber) {
        // Validate userName
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }

        // Validate email
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email format is invalid");
        }

        // Validate phone number
        if (phoneNumber == null || !phoneNumber.startsWith("+")) {
            throw new IllegalArgumentException("Phone number must include country prefix (e.g., +51987654321)");
        }
        if (phoneNumber.length() < 10) {
            throw new IllegalArgumentException("Phone number is too short (min 10 characters including prefix)");
        }

        this.userName = userName.trim();
        this.email = email.trim().toLowerCase();
        this.phoneNumber = phoneNumber.trim();
        return this;
    }

    /**
     * Updates user password (hashed password).
     * Business logic method with semantic naming.
     *
     * @param password New hashed password (already encrypted)
     * @return The updated User instance (fluent interface)
     */
    public User updatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = password;
        return this;
    }

    /**
     * Validates if the given raw password meets minimum requirements.
     * Used before hashing.
     *
     * @param rawPassword The plain text password to validate
     * @throws IllegalArgumentException if password is invalid
     */
    public static void validateRawPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 5) {
            throw new IllegalArgumentException("Password must be at least 5 characters long");
        }
    }
}


