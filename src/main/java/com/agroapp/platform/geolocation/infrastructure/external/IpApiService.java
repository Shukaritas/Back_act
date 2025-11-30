package com.agroapp.platform.geolocation.infrastructure.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class IpApiService {

    private static final Logger logger = LoggerFactory.getLogger(IpApiService.class);
    private static final String DEFAULT_LOCATION = "Lima, Peru";

    public String getLocationByIp(String ipAddress) {
        // Validamos si es localhost para no gastar peticiones
        if (ipAddress == null || ipAddress.trim().isEmpty() ||
                ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
            return DEFAULT_LOCATION;
        }

        try {
            // CAMBIO: Usamos la API de ip-api.com (http)
            // Esta API es mucho más permisiva para desarrollo
            URL url = new URL("http://ip-api.com/json/" + ipAddress);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            int status = connection.getResponseCode();
            if (status != 200) {
                logger.error("Error API HTTP: {}", status);
                return DEFAULT_LOCATION;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line);
            reader.close();

            // Mapeo manual del JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.toString());

            // ip-api.com usa "status": "fail" cuando falla
            if (root.has("status") && "fail".equals(root.get("status").asText())) {
                logger.warn("API Error: {}", root.has("message") ? root.get("message").asText() : "Unknown");
                return DEFAULT_LOCATION;
            }

            // CAMBIO: Nombres de campos específicos de ip-api.com
            String region = root.has("regionName") ? root.get("regionName").asText() : "";
            String country = root.has("country") ? root.get("country").asText() : "";

            if (region.isEmpty() && country.isEmpty()) return DEFAULT_LOCATION;

            String location = region.isEmpty() ? country :
                    country.isEmpty() ? region :
                            region + ", " + country;

            logger.info("Location resolved: {}", location);
            return location;

        } catch (Exception e) {
            logger.error("Excepción al obtener ubicación: {}", e.getMessage());
            return DEFAULT_LOCATION;
        }
    }
}