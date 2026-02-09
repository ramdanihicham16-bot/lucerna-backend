# Usar una imagen base ligera de Java 21
FROM eclipse-temurin:21-jdk-alpine

# Crear un volumen para archivos temporales
VOLUME /tmp

# Copiar el archivo JAR generado al contenedor
# Nota: El nombre del JAR debe coincidir con el generado por Maven (artifactId-version.jar)
COPY target/*.jar app.jar

# Comando de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
