package com.agroapp.platform.iam.interfaces.rest.resources;

public record UserResource(
        Long id,
        String userName,
        String email,
        String phoneNumber,
        String identificator,
        String location
) {
}

