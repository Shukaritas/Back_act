package com.agroapp.platform.geolocation.infrastructure.external;

import com.agroapp.platform.geolocation.infrastructure.external.dto.IpApiLocationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * External service for consuming the ipapi.co REST API.
 * Uses RestTemplate for synchronous HTTP requests.
 */
@Service
public class IpApiService {

    private static final Logger logger = LoggerFactory.getLogger(IpApiService.class);
    private static final String IPAPI_BASE_URL = "https://ipapi.co/{ip}/json/";
    private static final String DEFAULT_LOCATION = "Ubicación desconocida";

    private final RestTemplate restTemplate;

    public IpApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches location information from ipapi.co based on the provided IP address.
     *
     * @param ipAddress The IP address to geolocate
     * @return A formatted location string "City, Country" or default message if fails
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

            IpApiLocationResponse response = restTemplate.getForObject(
                IPAPI_BASE_URL,
                IpApiLocationResponse.class,
                ipAddress
            );

            if (response == null) {
                logger.error("Empty response from ipapi.co for IP: {}", ipAddress);
                return DEFAULT_LOCATION;
            }

            if (Boolean.TRUE.equals(response.error())) {
                logger.error("Error from ipapi.co: {} for IP: {}", response.reason(), ipAddress);
                return DEFAULT_LOCATION;
            }

            // Build location string: "City, Country"
            String city = response.city() != null ? response.city() : "";
            String country = response.countryName() != null ? response.countryName() : "";

            if (city.isEmpty() && country.isEmpty()) {
                logger.warn("No location data available for IP: {}", ipAddress);
                return DEFAULT_LOCATION;
            }

            String location = city.isEmpty() ? country :
                             country.isEmpty() ? city :
                             city + ", " + country;

            logger.info("Location resolved for IP {}: {}", ipAddress, location);
            return location;

        } catch (Exception e) {
            logger.error("Failed to fetch location for IP: {}. Error: {}", ipAddress, e.getMessage());
            return DEFAULT_LOCATION;
        }
    }
}

