# Etapa 1: Construcción
FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

# Copiamos el archivo pom.xml y los scripts del Maven Wrapper
COPY pom.xml .
COPY mvnw .
COPY .mvn ./.mvn

# Copiamos el resto del proyecto
COPY src ./src

# Construimos la aplicación
RUN ./mvnw clean package -DskipTests


# ETAPA DE EJECUCIÓN
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copiamos el JAR generado desde la etapa de build
COPY --from=build /app/target/hotelclover-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto de Spring Boot
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java","-jar","app.jar"]
