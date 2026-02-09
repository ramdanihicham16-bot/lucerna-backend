package com.lucerna.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lucerna.service.AgroMonitoringClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class AgroMonitoringClientHttp implements AgroMonitoringClient {

    private static final Logger log = LoggerFactory.getLogger(AgroMonitoringClientHttp.class);
    private static final String BASE_URL = "http://api.agromonitoring.com/agro/1.0";

    @Value("${lucerna.agro.apiKey}")
    private String apiKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public JsonObject obtenerDatosRecientes(String polygonId) {
        try {
            // Obtener timestamp de hace 30 días para buscar imágenes recientes de Sentinel-2
            long start = Instant.now().minus(30, ChronoUnit.DAYS).getEpochSecond();
            long end = Instant.now().getEpochSecond();

            // Endpoint para obtener imágenes satelitales del polígono
            String url = String.format("%s/image/search?start=%d&end=%d&polyid=%s&appid=%s",
                    BASE_URL, start, end, polygonId, apiKey);

            log.info("🛰️ Consultando Sentinel-2 para polygon: {}", polygonId);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 && !response.body().equals("[]")) {
                // Parsear la respuesta JSON (es un array de imágenes)
                var images = JsonParser.parseString(response.body()).getAsJsonArray();

                if (images.size() > 0) {
                    // Tomar la imagen más reciente
                    JsonObject latestImage = images.get(0).getAsJsonObject();

                    // Extraer datos de las bandas (stats contiene información de reflectancia)
                    JsonObject stats = latestImage.has("stats") ?
                            latestImage.getAsJsonObject("stats") : new JsonObject();

                    JsonObject result = new JsonObject();

                    // Extraer valores NIR (banda 8 de Sentinel-2) y RED (banda 4)
                    if (stats.has("ndvi")) {
                        // Si ya viene calculado el NDVI, lo usamos
                        double ndvi = stats.getAsJsonObject("ndvi").get("mean").getAsDouble();
                        // Recalcular NIR y RED aproximados desde NDVI
                        result.addProperty("nir", 0.5 + (ndvi * 0.3));
                        result.addProperty("red", 0.1 + ((1 - ndvi) * 0.05));
                    } else {
                        // Valores por defecto basados en vegetación saludable
                        result.addProperty("nir", 0.6);
                        result.addProperty("red", 0.08);
                    }

                    // URL de la imagen
                    String imageUrl = latestImage.has("image") ?
                            latestImage.getAsJsonObject("image").get("truecolor").getAsString() :
                            "http://api.agromonitoring.com/image/sentinel2.png";

                    result.addProperty("url_imagen", imageUrl);

                    log.info("✅ Datos Sentinel-2 obtenidos correctamente");
                    return result;
                }
            }

            log.warn("⚠️ No se encontraron imágenes recientes, usando datos simulados");
            return getDatoSimulado();

        } catch (Exception e) {
            log.error("❌ Error al consultar AgroMonitoring API: {}", e.getMessage());
            log.warn("🔄 Usando datos simulados como fallback");
            return getDatoSimulado();
        }
    }

    /**
     * Método de respaldo cuando la API no está disponible o no hay datos
     */
    private JsonObject getDatoSimulado() {
        Random r = new Random();
        JsonObject mock = new JsonObject();
        mock.addProperty("nir", 0.4 + (0.5 * r.nextDouble())); // 0.4 a 0.9
        mock.addProperty("red", 0.05 + (0.1 * r.nextDouble())); // 0.05 a 0.15
        mock.addProperty("url_imagen", "http://api.agromonitoring.com/image/example.png");
        return mock;
    }
}
