# 🛰️ Configuración de Fincas Reales con Sentinel-2

## ✅ Cambios Realizados

### 1. Fincas Reales Configuradas
He actualizado el sistema con **4 fincas reales** en zonas agrícolas de España:

| Finca | Ubicación | Coordenadas | Tipo de Cultivo | Polygon ID (REAL) |
|-------|-----------|-------------|-----------------|-------------------|
| Hacienda Los Girasoles | Sevilla, Andalucía | 37.389, -5.9845 | Trigo | **69869c92350273009d42f8cc** |
| Olivar Don Quijote | Toledo, Castilla-La Mancha | 39.863, -4.0275 | Olivo | **69869c9e2a38994bb473ab4e** |
| Naranjal del Mediterráneo | Valencia | 39.47, -0.3765 | Naranjo | **69869ca06f78d88f5784af79** |
| Huerta de la Vega | Murcia | 37.992, -1.1305 | Hortalizas | **69869ca31fd7b9e39f166560** |

### 2. Integración Real con API de AgroMonitoring
El cliente HTTP ahora:
- ✅ Consulta la API real de AgroMonitoring con tu API Key
- ✅ Obtiene imágenes Sentinel-2 de los últimos 30 días
- ✅ Extrae datos de bandas NIR y RED para calcular NDVI
- ✅ Incluye fallback a datos simulados si la API no responde

## 🔧 Cómo Crear Polígonos Reales en AgroMonitoring

Para que el sistema funcione con datos 100% reales de Sentinel-2, necesitas crear los polígonos en la API:

### Opción 1: Usar la API directamente (Recomendado)

```bash
# Crear un polígono para una finca (ejemplo: Sevilla)
curl -X POST "http://api.agromonitoring.com/agro/1.0/polygons?appid=d0373342d53a4d18df5edb5fd4b511fe" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Hacienda Los Girasoles",
    "geo_json": {
      "type": "Feature",
      "properties": {},
      "geometry": {
        "type": "Polygon",
        "coordinates": [[
          [-5.9900, 37.3850],
          [-5.9790, 37.3850],
          [-5.9790, 37.3930],
          [-5.9900, 37.3930],
          [-5.9900, 37.3850]
        ]]
      }
    }
  }'
```

La respuesta te dará un `id` que debes usar como `polygonId` en el código.

### Opción 2: Usar la interfaz web

1. Ve a: https://agromonitoring.com/
2. Inicia sesión con tu API Key
3. Crea polígonos dibujando sobre el mapa
4. Copia los IDs generados

### ✅ IDs Reales Ya Configurados

Los polígonos ya han sido creados en AgroMonitoring y los IDs reales están configurados en el código:

- **69869c92350273009d42f8cc** - Hacienda Los Girasoles (Sevilla)
- **69869c9e2a38994bb473ab4e** - Olivar Don Quijote (Toledo)
- **69869ca06f78d88f5784af79** - Naranjal del Mediterráneo (Valencia)
- **69869ca31fd7b9e39f166560** - Huerta de la Vega (Murcia)

Estos polígonos están activos y recibirán datos reales de Sentinel-2.

## 🚀 Cómo Probar el Sistema

1. **Ejecuta la aplicación:**
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Observa los logs:**
   - Verás mensajes como: `🛰️ Consultando Sentinel-2 para polygon: ...`
   - Si hay datos: `✅ Datos Sentinel-2 obtenidos correctamente`
   - Si no hay datos: `⚠️ No se encontraron imágenes recientes, usando datos simulados`

3. **Verifica en la base de datos H2:**
   - URL: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:lucernadb`
   - Usuario: `sa`
   - Password: (vacío)

## 📊 Estructura de Datos de Sentinel-2

El sistema obtiene:
- **NIR (Near Infrared)**: Banda 8 de Sentinel-2
- **RED**: Banda 4 de Sentinel-2
- **NDVI**: Calculado como (NIR - RED) / (NIR + RED)
- **URL de imagen**: Link a la imagen satelital en color verdadero

## 🔑 Tu API Key Actual

```
d0373342d53a4d18df5edb5fd4b511fe
```

Esta key está configurada en `src/main/resources/application.properties`

## 📝 Notas Importantes

1. **Límites de la API gratuita**:
   - 1000 llamadas/día
   - Imágenes Sentinel-2 cada 5 días (dependiendo de cobertura de nubes)

2. **Cobertura de nubes**:
   - Sentinel-2 puede no tener imágenes si hay mucha nubosidad
   - El sistema usa fallback automático a datos simulados

3. **Actualización de datos**:
   - Las imágenes Sentinel-2 se actualizan cada 5 días aproximadamente
   - El sistema busca imágenes de los últimos 30 días

## 🎯 Próximos Pasos

Para mejorar aún más el sistema:

1. **Crear polígonos reales** usando la API
2. **Implementar caché** para reducir llamadas a la API
3. **Añadir más métricas** (EVI, SAVI, etc.)
4. **Integrar pronóstico del tiempo** para predicciones más precisas
