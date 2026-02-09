package com.lucerna.service.notificacion;

import com.lucerna.model.RegistroSatelital;

public interface NotificadorService {
    void enviarAlerta(RegistroSatelital registro);
}
