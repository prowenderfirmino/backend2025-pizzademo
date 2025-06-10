# Use uma imagem base que inclua o JDK
# Exemplos:
# openjdk:17-jdk-slim (recomendado para produção e build)
# maven:3.9.6-openjdk-17 (já vem com Maven, simplifica o build)

# Exemplo 1: Usando openjdk com JDK
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copiar o Maven Wrapper e o pom.xml
COPY mvnw ./
COPY .mvn ./.mvn/
COPY pom.xml ./

# Copiar o código fonte
COPY src ./src

# Dar permissão de execução para o Maven Wrapper e buildar a aplicação
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# --- Segunda Stage (para imagem final menor, ideal para produção) ---
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o JAR compilado do estágio de build
COPY --from=builder /app/target/*.jar /app/app.jar

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
