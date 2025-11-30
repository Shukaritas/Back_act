package com.agroapp.platform.iam.domain.model.commands;

public record SignInCommand(
        String email,
        String password
) {
}

