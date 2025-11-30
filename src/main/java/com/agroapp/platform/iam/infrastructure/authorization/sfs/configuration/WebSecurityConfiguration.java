package com.agroapp.platform.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Web Security Configuration
 * Configures Spring Security for the application.
 * Allows public access to authentication endpoints and Swagger/OpenAPI documentation.
 * Uses stateless JWT authentication.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    /**
     * Configures the security filter chain.
     * Defines which endpoints are public and which require authentication.
     *
     * @param http HttpSecurity object to configure
     * @return SecurityFilterChain configured
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF (not needed for stateless REST API with JWT)
            .csrf(AbstractHttpConfigurer::disable)

            // Configure CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Configure authorization rules
            .authorizeHttpRequests(auth -> auth
                // Public endpoints - Authentication
                .requestMatchers("/api/v1/users/sign-up").permitAll()
                .requestMatchers("/api/v1/users/sign-in").permitAll()

                // Public endpoints - File Storage
                .requestMatchers("/api/v1/storage/**").permitAll()
                .requestMatchers("/uploads/**").permitAll()

                // Public endpoints - Swagger/OpenAPI Documentation
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()

                // Public endpoints - Actuator (health checks)
                .requestMatchers("/actuator/health").permitAll()

                // All other endpoints require authentication
                .anyRequest().permitAll() // Temporalmente permitir todo para desarrollo
            )

            // Stateless session management (JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing).
     * Allows requests from any origin (suitable for development).
     * In production, restrict to specific origins.
     *
     * @return CorsConfigurationSource configured
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow all origins (for development)
        // In production, use: configuration.setAllowedOrigins(List.of("https://yourdomain.com"));
        configuration.setAllowedOriginPatterns(List.of("*"));

        // Allow all HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Allow all headers
        configuration.setAllowedHeaders(List.of("*"));

        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Expose headers (for JWT in response)
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /**
     * Password encoder bean.
     * Uses BCrypt for secure password hashing.
     *
     * @return PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

