package com.agroapp.platform.shared.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for HTTP client beans.
 * Provides configured RestTemplate and ObjectMapper for consuming external APIs.
 */
@Configuration
public class HttpClientConfig {

    /**
     * Creates a RestTemplate bean with sensible defaults.
     * Includes connection and read timeout configurations.
     *
     * @return Configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10 seconds
        factory.setReadTimeout(10000); // 10 seconds
        return new RestTemplate(factory);
    }

    /**
     * Creates an ObjectMapper bean for JSON parsing.
     *
     * @return Configured ObjectMapper instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

