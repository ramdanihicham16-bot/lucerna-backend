package com.lucerna;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CrearPoligono {

    public static void main(String[] args) {
        // PEGUE SU API_KEY AQUÍ
        String API_KEY = "d0373342d53a4d18df5edb5fd4b511fe";

        if (API_KEY.isEmpty()) {
            System.err.println("❌ ERROR: Debes pegar tu API_KEY en la variable API_KEY.");
            return;
        }

        String url = "http://api.agromonitoring.com/agro/1.0/polygons?appid=" + API_KEY;

        String geoJsonBody = "{\"name\":\"Finca_Sevilla_Test\",\"geo_json\":{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[-5.9500,37.2000],[-5.9400,37.2000],[-5.9400,37.2100],[-5.9500,37.2100],[-5.9500,37.2000]]]}}}";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(geoJsonBody))
                .build();

        System.out.println("🚀 Enviando petición para registrar polígono...");

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("📡 Respuesta del servidor (" + response.statusCode() + "):");
            System.out.println(response.body());

            if (response.statusCode() == 201 || response.statusCode() == 200) {
                System.out.println("\n✅ Polígono registrado con éxito.");
            } else {
                System.out.println("\n⚠️ Hubo un problema al registrar el polígono.");
            }

        } catch (Exception e) {
            System.err.println("🔥 Error al realizar la petición: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
