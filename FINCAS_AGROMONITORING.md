# 🛰️ Fincas Guardadas en AgroMonitoring

## 📊 Resumen Total
**Total de polígonos:** 12 fincas
**API Key:** d0373342d53a4d18df5edb5fd4b511fe
**Usuario ID:** 697fb3851be1fb0008fa37e7

---

## 🌾 Fincas Antiguas (Creadas anteriormente)

### 1. GERB
- **ID:** `697fdd591fd7b9a438166521`
- **Ubicación:** Cataluña (cerca de Lleida)
- **Coordenadas Centro:** 0.826717, 41.821472
- **Área:** 4.08 hectáreas
- **Fecha Creación:** 2024-12-02

### 2. Secano
- **ID:** `697fe886c2d63e74b238de3b`
- **Ubicación:** Cataluña (cerca de Lleida)
- **Coordenadas Centro:** 0.798363, 41.788599
- **Área:** 12.56 hectáreas
- **Fecha Creación:** 2024-12-02

### 3. FSM
- **ID:** `697fee78f6d95d1600227335`
- **Ubicación:** Cataluña (cerca de Lleida)
- **Coordenadas Centro:** 0.805068, 41.747959
- **Área:** 2.65 hectáreas
- **Fecha Creación:** 2024-12-02

### 4. Finca La Paz
- **ID:** `6985ad986f78d8abaa84af72`
- **Ubicación:** Valencia
- **Coordenadas Centro:** -0.3763, 39.4699
- **Área:** 15.31 hectáreas
- **Fecha Creación:** 2025-02-03

### 5. Finca El Sol
- **ID:** `6985ad98d1895d18c1bade4c`
- **Ubicación:** Madrid
- **Coordenadas Centro:** -3.7033, 40.4167
- **Área:** 15.10 hectáreas
- **Fecha Creación:** 2025-02-03

### 6. Parcela Norte
- **ID:** `6985ad98f6d95d385622735e`
- **Ubicación:** Barcelona
- **Coordenadas Centro:** 2.1734, 41.3851
- **Área:** 14.88 hectáreas
- **Fecha Creación:** 2025-02-03

### 7. Huerto Familiar
- **ID:** `6985ad982a3899c46d73ab4c`
- **Ubicación:** Sevilla
- **Coordenadas Centro:** -5.9845, 37.3891
- **Área:** 15.75 hectáreas
- **Fecha Creación:** 2025-02-03

### 8. Terreno Secano
- **ID:** `6985ad98c2d63e570238de6c`
- **Ubicación:** Zaragoza
- **Coordenadas Centro:** -0.8891, 41.6488
- **Área:** 14.82 hectáreas
- **Fecha Creación:** 2025-02-03

---

## 🌟 Fincas Nuevas (Creadas hoy - 2025-02-06)

### 9. Hacienda Los Girasoles ⭐
- **ID:** `69869c92350273009d42f8cc`
- **Ubicación:** Sevilla, Andalucía
- **Coordenadas Centro:** -5.9845, 37.389
- **Área:** 86.64 hectáreas
- **Tipo Cultivo:** Trigo
- **Fecha Creación:** 2025-02-06
- **Estado:** ✅ Configurada en el código

### 10. Olivar Don Quijote ⭐
- **ID:** `69869c9e2a38994bb473ab4e`
- **Ubicación:** Toledo, Castilla-La Mancha
- **Coordenadas Centro:** -4.0275, 39.863
- **Área:** 83.70 hectáreas
- **Tipo Cultivo:** Olivo
- **Fecha Creación:** 2025-02-06
- **Estado:** ✅ Configurada en el código

### 11. Naranjal del Mediterráneo ⭐
- **ID:** `69869ca06f78d88f5784af79`
- **Ubicación:** Valencia
- **Coordenadas Centro:** -0.3765, 39.47
- **Área:** 84.18 hectáreas
- **Tipo Cultivo:** Naranjo
- **Fecha Creación:** 2025-02-06
- **Estado:** ✅ Configurada en el código

### 12. Huerta de la Vega ⭐
- **ID:** `69869ca31fd7b9e39f166560`
- **Ubicación:** Murcia
- **Coordenadas Centro:** -1.1305, 37.992
- **Área:** 85.94 hectáreas
- **Tipo Cultivo:** Hortalizas
- **Fecha Creación:** 2025-02-06
- **Estado:** ✅ Configurada en el código

---

## 📈 Estadísticas

### Por Área
- **Fincas grandes (>80 ha):** 4 fincas
  - Hacienda Los Girasoles: 86.64 ha
  - Huerta de la Vega: 85.94 ha
  - Naranjal del Mediterráneo: 84.18 ha
  - Olivar Don Quijote: 83.70 ha

- **Fincas medianas (10-20 ha):** 5 fincas
- **Fincas pequeñas (<10 ha):** 3 fincas

### Por Ubicación
- **Cataluña (Lleida):** 3 fincas
- **Andalucía (Sevilla):** 2 fincas
- **Valencia:** 2 fincas
- **Castilla-La Mancha (Toledo):** 1 finca
- **Madrid:** 1 finca
- **Barcelona:** 1 finca
- **Zaragoza:** 1 finca
- **Murcia:** 1 finca

### Área Total
**Suma total:** ~440 hectáreas aproximadamente

---

## 🎯 Fincas Activas en el Sistema LUCERNA

Actualmente, el sistema LUCERNA está configurado para monitorear las **4 fincas nuevas** (marcadas con ⭐):

1. Hacienda Los Girasoles
2. Olivar Don Quijote
3. Naranjal del Mediterráneo
4. Huerta de la Vega

Estas fincas tienen:
- ✅ IDs reales de AgroMonitoring
- ✅ Coordenadas GPS precisas
- ✅ Tipo de cultivo definido
- ✅ Integración con Sentinel-2

---

## 🔧 Comandos Útiles

### Ver todas las fincas
```bash
curl -X GET "http://api.agromonitoring.com/agro/1.0/polygons?appid=d0373342d53a4d18df5edb5fd4b511fe"
```

### Ver detalles de una finca específica
```bash
curl -X GET "http://api.agromonitoring.com/agro/1.0/polygons/69869c92350273009d42f8cc?appid=d0373342d53a4d18df5edb5fd4b511fe"
```

### Obtener imágenes satelitales de una finca
```bash
# Últimos 30 días
START=$(date -u -v-30d +%s)
END=$(date -u +%s)
curl -X GET "http://api.agromonitoring.com/agro/1.0/image/search?start=$START&end=$END&polyid=69869c92350273009d42f8cc&appid=d0373342d53a4d18df5edb5fd4b511fe"
```

### Eliminar una finca
```bash
curl -X DELETE "http://api.agromonitoring.com/agro/1.0/polygons/POLYGON_ID?appid=d0373342d53a4d18df5edb5fd4b511fe"
```

---

## 💡 Recomendaciones

1. **Limpiar fincas antiguas:** Si no necesitas las 8 fincas antiguas, considera eliminarlas para mantener la cuenta organizada.

2. **Añadir más fincas al sistema:** Puedes configurar las otras fincas en `LucernaApplication.java` si quieres monitorearlas también.

3. **Verificar datos Sentinel-2:** Las fincas nuevas son más grandes (>80 ha), lo que facilita la detección satelital.

4. **Monitoreo regular:** Sentinel-2 actualiza cada 5 días, así que ejecuta el sistema regularmente para obtener datos frescos.
[AgroService.java](src/main/java/com/lucerna/service/AgroService.java)