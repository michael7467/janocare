FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY auth-service ./auth-service
COPY professional-service ./professional-service
COPY booking-service ./booking-service
COPY payment-service ./payment-service
COPY notification-service ./notification-service

ARG SERVICE_NAME

RUN mvn -pl ${SERVICE_NAME} -am clean package -DskipTests \
    -Dmaven.wagon.http.retryHandler.count=5 \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -Dmaven.wagon.rto=600000

FROM eclipse-temurin:21-jre

WORKDIR /app

ARG SERVICE_NAME

COPY --from=build /app/${SERVICE_NAME}/target/quarkus-app/ ./quarkus-app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/quarkus-app/quarkus-run.jar"]