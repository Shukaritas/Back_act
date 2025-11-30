package com.agroapp.platform.plants.domain.model.aggregates;

import com.agroapp.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Field Aggregate Root
 * Main entity in the Plants bounded context.
 * Represents a physical field owned by a user where crops are grown.
 * Domain-driven design: encapsulates field management business logic.
 */
@Entity
@Getter
public class Field extends AuditableAbstractAggregateRoot<Field> {

    @Column(nullable = false)
    private Long userId;

    /**
     * Image stored as Base64 encoded string.
     * Uses @Lob (Large Object) to support very long text strings.
     * In MySQL, this maps to LONGTEXT type which can store up to 4GB.
     */
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl;

    private String name;
    private String location;
    private String fieldSize;

    /**
     * Default constructor required by JPA.
     */
    public Field() {
    }

    /**
     * Creates a new Field aggregate.
     * Business logic: A Field must always be owned by a user.
     *
     * @param userId The ID of the user who owns this field
     * @param imageUrl Optional Base64 encoded image string (stored directly in database)
     * @param name Name of the field
     * @param location Geographic location of the field
     * @param fieldSize Size description of the field
     */
    public Field(Long userId, String imageUrl, String name, String location, String fieldSize) {
        if (userId == null) {
            throw new IllegalArgumentException("Field must have an owner (userId cannot be null)");
        }
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.location = location;
        this.fieldSize = fieldSize;
    }

    /**
     * Updates the field's basic information.
     * Business logic method with semantic naming.
     *
     * @param name New name for the field
     * @param location New location
     * @param fieldSize New size description
     * @return The updated Field instance (fluent interface)
     */
    public Field updateFieldInformation(String name, String location, String fieldSize) {
        this.name = name;
        this.location = location;
        this.fieldSize = fieldSize;
        return this;
    }

    /**
     * Updates the field's image (Base64 encoded string).
     *
     * @param imageUrl New Base64 encoded image string
     * @return The updated Field instance (fluent interface)
     */
    public Field updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    /**
     * Validates if this field belongs to a specific user.
     * Business logic: encapsulates authorization logic.
     *
     * @param userId The user ID to check
     * @return true if the field belongs to the user
     */
    public boolean belongsToUser(Long userId) {
        return this.userId.equals(userId);
    }
}

