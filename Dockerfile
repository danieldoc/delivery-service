# --- Stage 1: Build ---
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY . .

RUN mvn clean install -DskipTests

# --- Stage 2: Final image ---
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /build/delivery-service-rest-api/target/delivery-service-rest-api.jar .
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "delivery-service-rest-api.jar"]