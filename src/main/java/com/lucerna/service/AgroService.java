package com.lucerna.service;
import com.lucerna.model.Finca;
import com.lucerna.repository.FincaRepository;

/**
 * Interfaz funcional para el procesamiento de datos satelitales de fincas.
 */
public interface AgroService {
    void procesarFinca(Finca finca);
    void sincronizarFincas(FincaRepository repositorio);
    void realizarRondaVigilancia();
}
