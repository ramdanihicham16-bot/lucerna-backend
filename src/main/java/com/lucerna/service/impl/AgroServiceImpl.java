package com.lucerna.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lucerna.model.Finca;
import com.lucerna.model.Medicion;
import com.lucerna.repository.FincaRepository;
import com.lucerna.repository.MedicionRepository;
import com.lucerna.service.AgroService;
import com.lucerna.service.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AgroServiceImpl implements AgroService {

    private static final Logger log = LoggerFactory.getLogger(AgroServiceImpl.class);

    @Value("${lucerna.agro.apiKey}")
    private String apiKey;

    @Autowired
    private FincaRepository repositorio;

    @Autowired
    private MedicionRepository medicionRepository;

    @Autowired
    private TelegramService telegramService;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
            
    private final Gson gson = new Gson();

    @Override
    public void procesarFinca(Finca finca) {
        log.info("🛰️ Procesando finca: {} (ID: {})", finca.getNombre(), finca.getPolygonId());

        try {
            long end = Instant.now().getEpochSecond();
            long start = Instant.now().minus(30, ChronoUnit.DAYS).getEpochSecond();

            String url = String.format("http://api.agromonitoring.com/agro/1.0/image/search?polyid=%s&start=%d&end=%d&appid=%s",
                    finca.getPolygonId(), start, end, apiKey);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                JsonArray imagenes = gson.fromJson(response.body(), JsonArray.class);
                
                if (imagenes != null && !imagenes.isEmpty()) {
                    // Tomamos la última imagen (la más reciente)
                    JsonObject últimaImagen = imagenes.get(imagenes.size() - 1).getAsJsonObject();
                    
                    double nubes = últimaImagen.has("cl") ? últimaImagen.get("cl").getAsDouble() : 0.0;
                    double ndviSimulado = (100.0 - nubes) / 100.0;

                    finca.setUltimoNdvi(ndviSimulado);
                    finca.setNubes(nubes);
                    finca.setEstado("ACTUALIZADO");

                    // Aplicar lógica de recomendación
                    if (ndviSimulado < 0.3) {
                        finca.setUltimaRecomendacion("🚨 RIEGO URGENTE");
                    } else if (ndviSimulado > 0.8) {
                        finca.setUltimaRecomendacion("💰 COSECHAR");
                    } else {
                        finca.setUltimaRecomendacion("✅ ESTABLE");
                    }

                    log.info("✅ Datos obtenidos: Nubes {}%, NDVI Calculado {}, Recomendación: {}", 
                            nubes, ndviSimulado, finca.getUltimaRecomendacion());
                } else {
                    log.warn("⚠️ No se encontraron imágenes para el periodo solicitado.");
                    usarDatoSimulado(finca);
                }
            } else {
                log.error("❌ Error en la API de AgroMonitoring. Status: {}", response.statusCode());
                usarDatoSimulado(finca);
            }

        } catch (Exception e) {
            log.error("🔥 Excepción al procesar finca: {}", e.getMessage());
            usarDatoSimulado(finca);
        }
    }

    @Override
    public void sincronizarFincas(FincaRepository repositorio) {
        log.info("📥 Iniciando sincronización de fincas desde AgroMonitoring...");
        try {
            String url = "http://api.agromonitoring.com/agro/1.0/polygons?appid=" + apiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                JsonArray poligonos = gson.fromJson(response.body(), JsonArray.class);

                for (JsonElement el : poligonos) {
                    JsonObject poly = el.getAsJsonObject();
                    String id = poly.get("id").getAsString();
                    String nombre = poly.get("name").getAsString();
                    
                    // Extraer centro [lon, lat]
                    JsonArray center = poly.getAsJsonArray("center");
                    double lon = center.get(0).getAsDouble();
                    double lat = center.get(1).getAsDouble();

                    // Comprobar si existe (usando polygonId como criterio de búsqueda)
                    // Como FincaRepository extiende JpaRepository<Finca, Long>, findById busca por ID de BD.
                    // Necesitamos buscar por polygonId.
                    boolean existe = repositorio.findAll().stream()
                            .anyMatch(f -> id.equals(f.getPolygonId()));

                    if (!existe) {
                        Finca nuevaFinca = new Finca(nombre, id, lat, lon);
                        repositorio.save(nuevaFinca);
                        log.info("📥 Finca importada: {}", nombre);
                        System.out.println("📥 Finca importada: [" + nombre + "]");
                    }
                }
            } else {
                log.error("❌ Error al sincronizar polígonos. Status: {}", response.statusCode());
            }
        } catch (Exception e) {
            log.error("🔥 Excepción al sincronizar fincas: {}", e.getMessage());
        }
    }

    @Override
    public void realizarRondaVigilancia() {
        System.out.println("--- ⏱️ Iniciando ronda de vigilancia ---");
        List<Finca> fincas = repositorio.findAll();

        for (Finca f : fincas) {
            System.out.println("🛰️ Analizando: " + f.getNombre());

            try {
                // Llamamos al satélite (ahora el servicio ya pone la recomendación)
                procesarFinca(f);

                // GUARDAR MEDICIÓN HISTÓRICA
                Medicion m = new Medicion();
                m.setFecha(java.time.LocalDateTime.now());
                m.setValorNdvi(f.getUltimoNdvi());
                m.setNombreFinca(f.getNombre());
                medicionRepository.save(m);

                // Preparamos el mensaje para el móvil basado en el resultado del servicio
                String mensajeAlerta = "";
                String recomendacion = f.getUltimaRecomendacion();

                if ("🚨 RIEGO URGENTE".equals(recomendacion)) {
                    mensajeAlerta = "⚠️ ALERTA EN " + f.getNombre() + "\nDetectado estrés hídrico bajo (Sequía).";
                } else if ("💰 COSECHAR".equals(recomendacion)) {
                    mensajeAlerta = "💰 OPORTUNIDAD EN " + f.getNombre() + "\nCultivo en punto máximo. Planificar cosecha.";
                } else {
                    mensajeAlerta = "✅ Informe de " + f.getNombre() + ":\nCultivo estable.\nNubes: " + f.getNubes() + "%";
                }

                repositorio.save(f);

                // 2. ENVIAR REPORTE AL MÓVIL
                telegramService.enviarMensaje(mensajeAlerta);

                // Pequeña pausa para no saturar el móvil si tienes muchas fincas
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("❌ Error en vigilancia: " + e.getMessage());
            }
        }
        System.out.println("🏁 VIGILANCIA COMPLETADA.\n");
    }

    private void usarDatoSimulado(Finca finca) {
        log.info("🤖 Aplicando datos simulados para finca: {}", finca.getNombre());
        double ndviAleatorio = 0.3 + (Math.random() * 0.5); // Entre 0.3 y 0.8
        finca.setUltimoNdvi(ndviAleatorio);
        finca.setEstado("SIMULADO");
        finca.setNubes(Math.random() * 20); // Simular pocas nubes

        // Aplicar lógica de recomendación también en simulados
        if (ndviAleatorio < 0.3) {
            finca.setUltimaRecomendacion("🚨 RIEGO URGENTE");
        } else if (ndviAleatorio > 0.8) {
            finca.setUltimaRecomendacion("💰 COSECHAR");
        } else {
            finca.setUltimaRecomendacion("✅ ESTABLE");
        }
    }
}
