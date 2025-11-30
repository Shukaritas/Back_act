package com.agroapp.platform.iam.interfaces.rest;

import com.agroapp.platform.iam.domain.model.commands.DeleteUserCommand;
import com.agroapp.platform.iam.domain.model.queries.GetUserByEmailQuery;
import com.agroapp.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.agroapp.platform.geolocation.domain.services.LocationService;
import com.agroapp.platform.iam.domain.services.UserCommandService;
import com.agroapp.platform.iam.domain.services.UserQueryService;
import com.agroapp.platform.iam.interfaces.rest.resources.*;
import com.agroapp.platform.iam.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final LocationService locationService;

    public UsersController(UserCommandService userCommandService, UserQueryService userQueryService,
                          LocationService locationService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.locationService = locationService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with validations: " +
                    "DNI must be exactly 8 digits, " +
                    "password must be at least 5 characters, " +
                    "phone number must include country prefix (e.g., +51987654321). " +
                    "User location is automatically detected from IP address."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User successfully created",
                    content = @Content(schema = @Schema(implementation = UserResource.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input - Check validations: " +
                            "DNI (8 digits), password (min 5 chars), phone (+prefix)"
            )
    })
    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpUserResource resource,
                                              HttpServletRequest request) {
        // Capture client IP address
        String ipAddress = getClientIpAddress(request);

        // Resolve location from IP using geolocation service
        String location = locationService.resolveLocationFromIp(ipAddress);

        // Create command with location
        var command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource, location);
        var user = userCommandService.handle(command);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    /**
     * Extracts the client's public IP address from the HTTP request.
     * Handles various proxy headers (X-Forwarded-For, X-Real-IP, etc.)
     *
     * @param request The HTTP servlet request
     * @return The client's IP address
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        // If multiple IPs in X-Forwarded-For, take the first one
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }
        return ipAddress;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInUserResource resource) {
        var command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var token = userCommandService.handle(command);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userQueryService.handle(new GetUserByEmailQuery(resource.email()));
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(user.get(), token.get());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id) {
        var query = new GetUserByIdQuery(id);
        var user = userQueryService.handle(query);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserResource> updateUserProfile(@PathVariable Long id, @RequestBody UpdateUserProfileResource resource) {
        var command = UpdateUserProfileCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var user = userCommandService.handle(command);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updateUserPassword(@PathVariable Long id, @RequestBody UpdateUserPasswordResource resource) {
        var command = UpdateUserPasswordCommandFromResourceAssembler.toCommandFromResource(id, resource);
        userCommandService.handle(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var command = new DeleteUserCommand(id);
        userCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}

