# JanoCare

JanoCare is a microservices-based healthcare platform built with Angular, Quarkus, PostgreSQL, Docker Compose, and a full observability stack.

## Architecture

The system follows a microservice architecture. The Angular frontend communicates with an NGINX API Gateway, which routes requests to independent backend services.

## Services

- auth-service: registration, login, JWT, user profile
- professional-service: professionals, profession types, specializations
- booking-service: appointments and booking slots
- payment-service: payment simulation
- notification-service: notifications and OTP messages

## Infrastructure

- Docker Compose for local orchestration
- PostgreSQL database per service
- Redis for temporary verification data
- NGINX as API Gateway
- Flyway for database migrations

## Observability

- Prometheus collects metrics from `/q/metrics`
- Grafana visualizes metrics and logs
- OpenTelemetry exports traces
- Jaeger shows distributed traces
- Loki and Promtail collect centralized logs

## Performance Testing

k6 is used to test API Gateway and backend performance under concurrent load.

## Run

```powershell
docker compose up -d