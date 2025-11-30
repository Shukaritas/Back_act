package com.agroapp.platform.geolocation.domain.services;

/**
 * Domain service interface for location resolution.
 * This interface abstracts the geolocation logic from the infrastructure details.
 */
public interface LocationService {

    /**
     * Resolves the geographical location based on an IP address.
     *
     * @param ipAddress The IP address to geolocate
     * @return A formatted location string (e.g., "Lima, Peru")
     */
    String resolveLocationFromIp(String ipAddress);
}

