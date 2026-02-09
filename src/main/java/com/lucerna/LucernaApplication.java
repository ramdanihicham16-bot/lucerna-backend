package com.lucerna;

import com.lucerna.model.Finca;
import com.lucerna.repository.FincaRepository;
import com.lucerna.service.AgroService;
import com.lucerna.service.TelegramService; // 👈 IMPRESCINDIBLE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class LucernaApplication {

    @Autowired
    private FincaRepository repositorio;

    @Autowired
    private AgroService agroService;

    @Autowired
    private TelegramService telegramService;

    public static void main(String[] args) {
        SpringApplication.run(LucernaApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Sincronizamos con AgroMonitoring
            agroService.sincronizarFincas(repositorio);
            
            // Ejecutamos la primera vigilancia manualmente al arrancar
            agroService.realizarRondaVigilancia();
        };
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void vigilarFincas() {
        agroService.realizarRondaVigilancia();
    }
}