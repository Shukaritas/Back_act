package com.agroapp.platform.plants.domain.model.commands;

public record CreateFieldCommand(
        Long userId,
        String imageUrl,
        String name,
        String location,
        String fieldSize
) {
}

