package com.agroapp.platform.plants.domain.model.entities;

import com.agroapp.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * ProgressHistory Entity (1:1 relationship with Field)
 * Tracks agricultural activities and maintenance history for a field.
 * Created automatically when a Field is created.
 * Domain-driven design: encapsulates field maintenance tracking logic.
 */
@Entity
@Getter
public class ProgressHistory extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long fieldId;

    private LocalDateTime watered;
    private LocalDateTime fertilized;
    private LocalDateTime pests;

    /**
     * Default constructor required by JPA.
     */
    public ProgressHistory() {
    }

    /**
     * Creates a new ProgressHistory with default timestamps (current time).
     * Business logic: Automatically initializes all activity dates to now.
     *
     * @param fieldId The ID of the associated field
     */
    public ProgressHistory(Long fieldId) {
        if (fieldId == null) {
            throw new IllegalArgumentException("ProgressHistory must be associated with a field (fieldId cannot be null)");
        }
        this.fieldId = fieldId;
        this.watered = LocalDateTime.now();
        this.fertilized = LocalDateTime.now();
        this.pests = LocalDateTime.now();
    }

    /**
     * Creates a new ProgressHistory with specific dates.
     *
     * @param fieldId The ID of the associated field
     * @param watered Last watering date
     * @param fertilized Last fertilization date
     * @param pests Last pest control date
     */
    public ProgressHistory(Long fieldId, LocalDateTime watered, LocalDateTime fertilized, LocalDateTime pests) {
        if (fieldId == null) {
            throw new IllegalArgumentException("ProgressHistory must be associated with a field (fieldId cannot be null)");
        }
        this.fieldId = fieldId;
        this.watered = watered;
        this.fertilized = fertilized;
        this.pests = pests;
    }

    /**
     * Updates all maintenance activity dates.
     * Note: Kept for backward compatibility, but prefer more specific methods.
     *
     * @param watered New watering date
     * @param fertilized New fertilization date
     * @param pests New pest control date
     * @return The updated ProgressHistory instance (fluent interface)
     */
    public ProgressHistory update(LocalDateTime watered, LocalDateTime fertilized, LocalDateTime pests) {
        this.watered = watered;
        this.fertilized = fertilized;
        this.pests = pests;
        return this;
    }

    /**
     * Records a watering event.
     * Business logic method with domain-specific semantics.
     *
     * @return The updated ProgressHistory instance (fluent interface)
     */
    public ProgressHistory recordWatering() {
        this.watered = LocalDateTime.now();
        return this;
    }

    /**
     * Records a watering event at a specific date.
     *
     * @param wateredDate The date when watering occurred
     * @return The updated ProgressHistory instance (fluent interface)
     */
    public ProgressHistory recordWatering(LocalDateTime wateredDate) {
        this.watered = wateredDate;
        return this;
    }

    /**
     * Records a fertilization event.
     *
     * @return The updated ProgressHistory instance (fluent interface)
     */
    public ProgressHistory recordFertilization() {
        this.fertilized = LocalDateTime.now();
        return this;
    }

    /**
     * Records a fertilization event at a specific date.
     *
     * @param fertilizedDate The date when fertilization occurred
     * @return The updated ProgressHistory instance (fluent interface)
     */
    public ProgressHistory recordFertilization(LocalDateTime fertilizedDate) {
        this.fertilized = fertilizedDate;
        return this;
    }

    /**
     * Records a pest control event.
     *
     * @return The updated ProgressHistory instance (fluent interface)
     */
    public ProgressHistory recordPestControl() {
        this.pests = LocalDateTime.now();
        return this;
    }

    /**
     * Records a pest control event at a specific date.
     *
     * @param pestsDate The date when pest control occurred
     * @return The updated ProgressHistory instance (fluent interface)
     */
    public ProgressHistory recordPestControl(LocalDateTime pestsDate) {
        this.pests = pestsDate;
        return this;
    }
}

