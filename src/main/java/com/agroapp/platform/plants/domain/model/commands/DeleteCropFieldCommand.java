package com.agroapp.platform.plants.domain.model.commands;

/**
 * Command to delete a CropField by its ID.
 * Part of CQRS pattern - represents a write operation.
 */
public record DeleteCropFieldCommand(Long id) {
}

