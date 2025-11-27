# 1) Build stage (optional but nice)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven wrapper & pom
COPY pom.xml mvnw* ./
COPY .mvn .mvn

# Pre-download dependencies (faster rebuilds)
RUN ./mvnw -q dependency:go-offline

# Copy source and build jar
COPY src src
RUN ./mvnw clean package -DskipTests

# 2) Runtime stage: minimal JRE image
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the fat jar from build stage
COPY --from=build /app/target/spark-hello-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# Expose the port your Spark app listens on (adjust if needed)
EXPOSE 8080

# Default command
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
