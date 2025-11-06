# Use Maven com Eclipse Temurin 21 (build e runtime)
FROM maven:3.9.11-eclipse-temurin-21-alpine AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o pom.xml e faz o download das dependências (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código e faz o build do jar
COPY src ./src
RUN mvn clean package -DskipTests

# Fase final (runtime)
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o jar da fase de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]