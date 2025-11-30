package com.agroapp.platform.plants.domain.model.entities;

import com.agroapp.platform.plants.domain.model.valueobjects.CropFieldStatus;
import com.agroapp.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * CropField Entity (1:1 relationship with Field)
 * Represents the agricultural details of a field including crop type, soil conditions, and status.
 * Domain-driven design: encapsulates crop management business logic.
 */
@Entity
@Getter
public class CropField extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long fieldId;

    private String crop;
    private String soilType;
    private String sunlight;
    private String watering;
    private LocalDateTime plantingDate;
    private LocalDateTime harvestDate;

    @Enumerated(EnumType.STRING)
    private CropFieldStatus status;

    /**
     * Default constructor required by JPA.
     */
    public CropField() {
    }

    /**
     * Creates a new CropField entity.
     * Business logic: A CropField must always be associated with a Field (1:1 relationship).
     *
     * @param fieldId The ID of the associated field
     * @param crop Name of the crop being grown
     * @param soilType Type of soil in the field
     * @param sunlight Sunlight conditions
     * @param watering Watering requirements/schedule
     * @param plantingDate Date when the crop was planted
     * @param harvestDate Expected harvest date
     * @param status Current health status of the crop
     */
    public CropField(Long fieldId, String crop, String soilType, String sunlight, String watering,
                     LocalDateTime plantingDate, LocalDateTime harvestDate, CropFieldStatus status) {
        if (fieldId == null) {
            throw new IllegalArgumentException("CropField must be associated with a field (fieldId cannot be null)");
        }
        this.fieldId = fieldId;
        this.crop = crop;
        this.soilType = soilType;
        this.sunlight = sunlight;
        this.watering = watering;
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
        this.status = status != null ? status : CropFieldStatus.Healthy;
    }

    /**
     * Updates the crop type.
     * Business logic method with semantic naming.
     *
     * @param crop New crop name
     * @return The updated CropField instance (fluent interface)
     */
    public CropField updateCrop(String crop) {
        this.crop = crop;
        return this;
    }

    /**
     * Updates the crop field status.
     * Business logic: encapsulates status management.
     *
     * @param status New status
     * @return The updated CropField instance (fluent interface)
     */
    public CropField updateStatus(CropFieldStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
        return this;
    }

    /**
     * Marks the crop as requiring attention.
     * Business logic method with domain-specific semantics.
     *
     * @return The updated CropField instance (fluent interface)
     */
    public CropField markAsNeedsAttention() {
        this.status = CropFieldStatus.Attention;
        return this;
    }

    /**
     * Marks the crop as critical (requires immediate intervention).
     *
     * @return The updated CropField instance (fluent interface)
     */
    public CropField markAsCritical() {
        this.status = CropFieldStatus.Critical;
        return this;
    }

    /**
     * Marks the crop as healthy.
     *
     * @return The updated CropField instance (fluent interface)
     */
    public CropField markAsHealthy() {
        this.status = CropFieldStatus.Healthy;
        return this;
    }

    /**
     * Reschedules the harvest date.
     *
     * @param newHarvestDate New expected harvest date
     * @return The updated CropField instance (fluent interface)
     */
    public CropField rescheduleHarvest(LocalDateTime newHarvestDate) {
        this.harvestDate = newHarvestDate;
        return this;
    }

    /**
     * Updates agricultural conditions (soil, sunlight, watering).
     *
     * @param soilType New soil type description
     * @param sunlight New sunlight conditions
     * @param watering New watering schedule
     * @return The updated CropField instance (fluent interface)
     */
    public CropField updateConditions(String soilType, String sunlight, String watering) {
        this.soilType = soilType;
        this.sunlight = sunlight;
        this.watering = watering;
        return this;
    }

    /**
     * Checks if the crop field is in good health.
     *
     * @return true if status is Healthy
     */
    public boolean isHealthy() {
        return this.status == CropFieldStatus.Healthy;
    }

    /**
     * Checks if the crop field needs attention.
     *
     * @return true if status is Attention or Critical
     */
    public boolean needsAttention() {
        return this.status == CropFieldStatus.Attention || this.status == CropFieldStatus.Critical;
    }
}

