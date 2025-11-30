package com.agroapp.platform.plants.domain.model.aggregates;

import com.agroapp.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Task Aggregate Root
 * Represents a task associated with a Field.
 * Domain-driven design: encapsulates task management business logic.
 */
@Entity
@Getter
public class Task extends AuditableAbstractAggregateRoot<Task> {

    @Column(nullable = false)
    private Long fieldId;

    private String description;
    private LocalDateTime dueDate;

    /**
     * Default constructor required by JPA.
     */
    public Task() {
    }

    /**
     * Creates a new Task aggregate.
     * Business logic: A Task must always be associated with a Field.
     *
     * @param fieldId The ID of the field this task is associated with
     * @param description Description of the task
     * @param dueDate Due date for completing the task
     */
    public Task(Long fieldId, String description, LocalDateTime dueDate) {
        if (fieldId == null) {
            throw new IllegalArgumentException("Task must be associated with a field (fieldId cannot be null)");
        }
        this.fieldId = fieldId;
        this.description = description;
        this.dueDate = dueDate;
    }

    /**
     * Updates the task information.
     * Business logic method with semantic naming.
     * Note: Kept for backward compatibility, but prefer more specific methods.
     *
     * @param fieldId The field ID (can be changed to reassign task)
     * @param description New description
     * @param dueDate New due date
     * @return The updated Task instance (fluent interface)
     */
    public Task update(Long fieldId, String description, LocalDateTime dueDate) {
        if (fieldId == null) {
            throw new IllegalArgumentException("Task must be associated with a field (fieldId cannot be null)");
        }
        this.fieldId = fieldId;
        this.description = description;
        this.dueDate = dueDate;
        return this;
    }

    /**
     * Updates only the task description.
     *
     * @param description New description
     * @return The updated Task instance (fluent interface)
     */
    public Task updateDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Reschedules the task to a new due date.
     *
     * @param newDueDate New due date
     * @return The updated Task instance (fluent interface)
     */
    public Task reschedule(LocalDateTime newDueDate) {
        this.dueDate = newDueDate;
        return this;
    }

    /**
     * Reassigns the task to a different field.
     *
     * @param newFieldId The new field ID
     * @return The updated Task instance (fluent interface)
     */
    public Task reassignToField(Long newFieldId) {
        if (newFieldId == null) {
            throw new IllegalArgumentException("Cannot reassign task to null field");
        }
        this.fieldId = newFieldId;
        return this;
    }

    /**
     * Checks if the task is overdue.
     * Business logic: encapsulates task status calculation.
     *
     * @return true if the task is past its due date
     */
    public boolean isOverdue() {
        return dueDate != null && LocalDateTime.now().isAfter(dueDate);
    }

    /**
     * Validates if this task belongs to a specific field.
     *
     * @param fieldId The field ID to check
     * @return true if the task belongs to the field
     */
    public boolean belongsToField(Long fieldId) {
        return this.fieldId.equals(fieldId);
    }
}

