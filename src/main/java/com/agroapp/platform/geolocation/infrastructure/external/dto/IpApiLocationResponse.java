package com.agroapp.platform.geolocation.infrastructure.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for mapping the response from ipapi.co external API.
 * Only maps the fields needed for location information.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IpApiLocationResponse(
        @JsonProperty("city")
        String city,

        @JsonProperty("region")
        String region,

        @JsonProperty("country_name")
        String countryName,

        @JsonProperty("error")
        Boolean error,

        @JsonProperty("reason")
        String reason
) {
}

