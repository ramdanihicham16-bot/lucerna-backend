package com.lucerna.service.notificacion;

import com.lucerna.model.RegistroSatelital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramNotificadorService implements NotificadorService {

    private static final Logger log = LoggerFactory.getLogger(TelegramNotificadorService.class);

    @Override
    public void enviarAlerta(RegistroSatelital registro) {
        log.info("[TELEGRAM ESQUELETO] Enviando a Telegram para finca: {}. Recomendación: {}", 
                registro.getFinca().getNombre(), registro.getRecomendacion());
        // Implementar con RestTemplate o WebClient hacia la API de Telegram Bot
    }
}
