package com.agroapp.platform.iam.domain.model.commands;

public record SignUpCommand(
        String userName,
        String email,
        String password,
        String phoneNumber,
        String identificator,
        String location
) {
}

