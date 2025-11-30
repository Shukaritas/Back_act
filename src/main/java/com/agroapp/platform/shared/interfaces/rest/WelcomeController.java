package com.agroapp.platform.shared.interfaces.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "AgroApp", description = "API Endpoint configurations")
@RestController
@RequestMapping("/")
public class WelcomeController {
    @GetMapping
    @Operation(summary = "AgroApp Logic", description = "Provides information and available endpoints of the API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public Map<String, Object> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to AgroApp Backend API");
        response.put("status", "running");
        response.put("version", "1.0.0");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("API Documentation", "/swagger-ui.html");
        endpoints.put("API Docs JSON", "/v3/api-docs");
        endpoints.put("Community Recommendations", "/api/v1/community-recommendations");
        endpoints.put("Crop Fields", "/api/v1/crop-fields");
        endpoints.put("Fields", "/api/v1/fields");
        endpoints.put("Progress History", "/api/v1/progress");
        endpoints.put("Tasks", "/api/v1/tasks");
        endpoints.put("Users", "/api/v1/users");
        endpoints.put("Storage", "/api/v1/storage");
        response.put("endpoints", endpoints);

        return response;
    }
}
