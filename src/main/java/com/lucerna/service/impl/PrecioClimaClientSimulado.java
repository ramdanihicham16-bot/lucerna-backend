package com.lucerna.service.impl;

import com.lucerna.service.PrecioClimaClient;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PrecioClimaClientSimulado implements PrecioClimaClient {
    private final Random random = new Random();

    @Override
    public boolean vaALlover(double lat, double lon) {
        // Simulación: 30% de probabilidad de lluvia
        return random.nextDouble() < 0.3;
    }

    @Override
    public boolean precioSube(String tipoCultivo) {
        // Simulación: 50% de probabilidad de que el precio suba
        return random.nextBoolean();
    }
}
