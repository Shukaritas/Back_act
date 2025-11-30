package com.agroapp.platform.iam.interfaces.rest.resources;

public record SignInUserResource(
        String email,
        String password
) {
}

