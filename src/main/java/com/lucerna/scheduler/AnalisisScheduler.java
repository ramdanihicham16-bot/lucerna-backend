package com.lucerna.scheduler;

import com.lucerna.model.Finca;
import com.lucerna.repository.FincaRepository;
import com.lucerna.service.AgroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalisisScheduler {

    private static final Logger log = LoggerFactory.getLogger(AnalisisScheduler.class);

    private final FincaRepository fincaRepository;
    private final AgroService agroService;

    public AnalisisScheduler(FincaRepository fincaRepository, AgroService agroService) {
        this.fincaRepository = fincaRepository;
        this.agroService = agroService;
    }

    /**
     * Ejecuta el análisis de todas las fincas cada día a las 00:00.
     * También se puede invocar manualmente desde el controlador.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void ejecutarAnalisisDiario() {
        log.info("⏰ Iniciando análisis diario programado...");
        List<Finca> fincas = fincaRepository.findAll();
        
        for (Finca finca : fincas) {
            try {
                agroService.procesarFinca(finca);
                fincaRepository.save(finca);
            } catch (Exception e) {
                log.error("❌ Error procesando finca {}: {}", finca.getNombre(), e.getMessage());
            }
        }
        log.info("✅ Análisis diario completado.");
    }
}
