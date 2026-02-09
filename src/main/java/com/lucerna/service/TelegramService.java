package com.lucerna.service;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class TelegramService {

    private static final String BOT_TOKEN = "7970508420:AAGLD1KMf3N_LN39gD5vegfB3dgpgTqur8k";
    private static final String CHAT_ID = "7902238231";

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public void enviarMensaje(String mensaje) {
        if ("X".equals(BOT_TOKEN) || "X".equals(CHAT_ID)) {
            System.out.println("⚠️ TelegramService: BOT_TOKEN o CHAT_ID no configurados.");
            return;
        }

        try {
            String url = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                    BOT_TOKEN, CHAT_ID, URLEncoder.encode(mensaje, StandardCharsets.UTF_8));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(body -> System.out.println("📩 Telegram Resp: " + body))
                    .exceptionally(e -> {
                        System.err.println("🔥 Error asíncrono en Telegram: " + e.getMessage());
                        return null;
                    });

        } catch (Exception e) {
            System.err.println("🔥 Error al preparar mensaje de Telegram: " + e.getMessage());
        }
    }
}
