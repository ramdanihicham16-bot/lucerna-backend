package com.lucerna.controller;

import com.lucerna.model.Finca;
import com.lucerna.repository.FincaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fincas")
public class FincaController {

    @Autowired
    private FincaRepository fincaRepository;

    @GetMapping
    public List<Finca> listarFincas() {
        return fincaRepository.findAll();
    }
}
