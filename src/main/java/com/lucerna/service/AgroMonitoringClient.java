package com.lucerna.service;

import com.google.gson.JsonObject;

public interface AgroMonitoringClient {
    JsonObject obtenerDatosRecientes(String polygonId);
}
