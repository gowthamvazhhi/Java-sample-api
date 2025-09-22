# Multi-stage Docker build for tata-java-simple-api

# Build stage
FROM openjdk:17-slim AS builder
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw dependency:resolve -B
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

RUN groupadd -r appuser && useradd -r -g appuser appuser
WORKDIR /app
COPY --from=builder /app/target/tata-java-simple-api-*.jar app.jar
RUN chown -R appuser /app
USER appuser
EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/api/health || exit 1

ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

LABEL maintainer="rsannareddy"
LABEL version="1.0.0"
LABEL description="Simple Java Spring Boot API"
LABEL application="tata-java-simple-api"
