package com.lucerna.controller;

import com.lucerna.model.Medicion;
import com.lucerna.repository.FincaRepository;
import com.lucerna.repository.MedicionRepository;
import com.lucerna.service.AgroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private FincaRepository repositorio;

    @Autowired
    private MedicionRepository medicionRepository;

    @Autowired
    private AgroService agroService;

    @GetMapping
    public String mostrarInicio(Model model) {
        model.addAttribute("fincas", repositorio.findAll());

        // Obtener todas las mediciones ordenadas por fecha
        List<Medicion> todasMediciones = medicionRepository.findAll().stream()
                .sorted(Comparator.comparing(Medicion::getFecha))
                .collect(Collectors.toList());

        // Obtener las últimas 10 fechas únicas para el eje X
        List<LocalDateTime> ultimasFechas = todasMediciones.stream()
                .map(Medicion::getFecha)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .limit(10)
                .sorted()
                .collect(Collectors.toList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        List<String> etiquetas = ultimasFechas.stream()
                .map(f -> f.format(formatter))
                .collect(Collectors.toList());

        // Agrupar mediciones por finca
        Map<String, List<Double>> datosPorFinca = todasMediciones.stream()
                .collect(Collectors.groupingBy(Medicion::getNombreFinca))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<Medicion> ms = entry.getValue();
                            // Para cada una de las 15 fechas, buscamos si hay medición
                            return ultimasFechas.stream()
                                    .map(fecha -> ms.stream()
                                            .filter(m -> m.getFecha().equals(fecha))
                                            .map(Medicion::getValorNdvi)
                                            .findFirst()
                                            .orElse(null)) // null si no hay dato en esa fecha
                                    .collect(Collectors.toList());
                        }
                ));

        model.addAttribute("etiquetas", etiquetas);
        model.addAttribute("datosFincas", datosPorFinca);

        return "index";
    }

    @PostMapping("/analizar")
    public String analizar() {
        agroService.realizarRondaVigilancia();
        return "redirect:/";
    }
}
