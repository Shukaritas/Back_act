package com.agroapp.platform.geolocation.infrastructure.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agroapp.platform.geolocation.infrastructure.external.dto.IpApiLocationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * External service for consuming the ipapi.co REST API.
 * Uses native HttpURLConnection for synchronous HTTP requests with custom User-Agent.
 */
@Service
public class IpApiService {

    private static final Logger logger = LoggerFactory.getLogger(IpApiService.class);
    private static final String IPAPI_BASE_URL = "https://ipapi.co/";
    private static final String DEFAULT_LOCATION = "Ubicación desconocida";
    private static final String USER_AGENT = "java-ipapi-v1.02";

    private final ObjectMapper objectMapper;

    public IpApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Fetches location information from ipapi.co based on the provided IP address.
     *
     * @param ipAddress The IP address to geolocate
     * @return A formatted location string "Region, Country" or default message if fails
     */
    public String getLocationByIp(String ipAddress) {
        try {
            // Validate IP address
            if (ipAddress == null || ipAddress.trim().isEmpty() ||
                ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                logger.warn("Local or invalid IP address detected: {}", ipAddress);
                return DEFAULT_LOCATION;
            }

            logger.info("Fetching location for IP: {}", ipAddress);

            // Create URL and connection with custom User-Agent
            URL url = new URL(IPAPI_BASE_URL + ipAddress + "/json/");
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);

            // Read response
            StringBuilder jsonResponse = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResponse.append(line);
                }
            }

            // Parse JSON response
            IpApiLocationResponse response = objectMapper.readValue(
                jsonResponse.toString(),
                IpApiLocationResponse.class
            );

            if (response == null) {
                logger.error("Empty response from ipapi.co for IP: {}", ipAddress);
                return DEFAULT_LOCATION;
            }

            if (Boolean.TRUE.equals(response.error())) {
                logger.error("Error from ipapi.co: {} for IP: {}", response.reason(), ipAddress);
                return DEFAULT_LOCATION;
            }

            // Build location string: "Region, Country"
            String region = response.region() != null ? response.region() : "";
            String country = response.countryName() != null ? response.countryName() : "";

            if (region.isEmpty() && country.isEmpty()) {
                logger.warn("No location data available for IP: {}", ipAddress);
                return DEFAULT_LOCATION;
            }

            String location = region.isEmpty() ? country :
                             country.isEmpty() ? region :
                             region + ", " + country;

            logger.info("Location resolved for IP {}: {}", ipAddress, location);
            return location;

        } catch (Exception e) {
            logger.error("Failed to fetch location for IP: {}. Error: {}", ipAddress, e.getMessage());
            return DEFAULT_LOCATION;
        }
    }
}

