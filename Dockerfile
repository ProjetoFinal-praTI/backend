# =========================
# Fase de build com Maven + JDK 21
# =========================
FROM eclipse-temurin:21-jdk AS build

# Instala Maven
RUN apt-get update && apt-get install -y maven
COPY . .

# Compila o projeto
RUN mvn clean package -DskipTests

# =========================
# Fase de runtime com JDK 21 slim
# =========================
FROM eclipse-temurin:21-jdk-slim

EXPOSE 8080
# Copia o artefato da fase de build
COPY --from=build /target/backend-0.0.1-SNAPSHOT.jar app.jar

# Executa a aplicação
ENTRYPOINT [ "java", "-jar", "app.jar" ]
