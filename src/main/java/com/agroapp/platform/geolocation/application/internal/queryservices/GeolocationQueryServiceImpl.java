package com.agroapp.platform.geolocation.application.internal.queryservices;

import com.agroapp.platform.geolocation.domain.services.LocationService;
import com.agroapp.platform.geolocation.infrastructure.external.IpApiService;
import org.springframework.stereotype.Service;

/**
 * Application service that orchestrates geolocation queries.
 * Implements the domain service interface and delegates to infrastructure services.
 */
@Service
public class GeolocationQueryServiceImpl implements LocationService {

    private final IpApiService ipApiService;

    public GeolocationQueryServiceImpl(IpApiService ipApiService) {
        this.ipApiService = ipApiService;
    }

    /**
     * Resolves location from IP address by delegating to the external API service.
     *
     * @param ipAddress The IP address to resolve
     * @return A formatted location string
     */
    @Override
    public String resolveLocationFromIp(String ipAddress) {
        return ipApiService.getLocationByIp(ipAddress);
    }
}

