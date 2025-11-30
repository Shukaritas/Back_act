package com.agroapp.platform.plants.interfaces.rest.resources;

public record CreateFieldResource(
        Long userId,
        String imageUrl,
        String name,
        String location,
        String fieldSize
) {
}

