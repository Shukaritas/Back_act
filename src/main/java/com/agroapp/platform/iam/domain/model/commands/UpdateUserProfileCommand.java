package com.agroapp.platform.iam.domain.model.commands;

public record UpdateUserProfileCommand(
        Long userId,
        String userName,
        String email,
        String phoneNumber,
        boolean isLocationPublic
) {
}

