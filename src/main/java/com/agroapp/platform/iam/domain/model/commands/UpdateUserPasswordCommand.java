package com.agroapp.platform.iam.domain.model.commands;

public record UpdateUserPasswordCommand(
        Long userId,
        String currentPassword,
        String newPassword
) {
}

