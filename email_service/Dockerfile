
# =========================
# 1. Etapa de build
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Gera o .jar da aplicação
RUN mvn clean package -DskipTests


# =========================
# 2. Etapa de execução
# =========================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o jar gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Porta da sua aplicação
EXPOSE 8080

# Comando que inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]