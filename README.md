# JanoCare

JanoCare is a distributed healthcare platform developed as the project work for the **Software Architecture for Web Applications and Middleware (SWAM)** course.

The project demonstrates the design and implementation of a modern cloud-native microservices architecture using **Quarkus**, **Angular**, **Docker**, and a complete **observability stack**. The platform follows Domain-Driven Design (DDD), Hexagonal Architecture (Ports & Adapters), and CQRS principles to provide a modular, scalable, and maintainable healthcare system.

---

# Features

- User registration and authentication with JWT
- OTP-based account verification
- Patient and healthcare professional roles
- Professional profile management
- Profession types and specializations
- Appointment slot generation
- Appointment booking workflow
- Payment transaction management
- Notification service
- Centralized API Gateway
- Metrics, logging, and distributed tracing
- Performance testing using k6

---

# Architecture

The platform follows a **microservices architecture**.

```
Angular Frontend
        │
        ▼
NGINX API Gateway
        │
 ┌──────┼──────────────────────────────┐
 │      │      │       │              │
 ▼      ▼      ▼       ▼              ▼
Auth  Professional  Booking  Payment  Notification
 │        │          │         │           │
 ▼        ▼          ▼         ▼           ▼
PostgreSQL PostgreSQL PostgreSQL PostgreSQL PostgreSQL
```

Each microservice owns:

- its own business logic
- its own REST API
- its own PostgreSQL database
- independent deployment lifecycle

No service accesses another service's database directly.

---

# Technology Stack

## Frontend

- Angular
- TypeScript
- Angular Material
- RxJS

## Backend

- Quarkus
- Jakarta REST
- Hibernate ORM
- Panache
- Flyway
- SmallRye JWT
- MicroProfile REST Client

## Database

- PostgreSQL (Database-per-Service)

## Infrastructure

- Docker
- Docker Compose
- NGINX API Gateway
- Redis

## Observability

- Prometheus
- Grafana
- Loki
- Promtail
- Jaeger
- OpenTelemetry

## Performance Testing

- k6

---

# Microservices

## Authentication Service

Responsible for:

- User registration
- Login
- JWT generation
- OTP verification
- User profile management
- Country / State / City management

---

## Professional Service

Responsible for:

- Healthcare professionals
- Profession types
- Qualifications
- Experiences
- Memberships
- Reviews
- Specializations

---

## Booking Service

Responsible for:

- Booking slots
- Appointment scheduling
- Appointment management
- Booking cancellation

---

## Payment Service

Responsible for:

- Payment transactions
- Payment status
- Refund management

---

## Notification Service

Responsible for:

- OTP notifications
- Appointment notifications
- Email notifications
- Notification history

---

# Observability

The platform implements the three pillars of observability.

### Metrics

- Prometheus scrapes every Quarkus service
- Grafana visualizes:
  - HTTP requests
  - JVM metrics
  - Memory usage
  - Response latency

### Logs

- Docker container logs
- Promtail collection
- Loki storage
- LogQL querying through Grafana

### Distributed Tracing

- OpenTelemetry instrumentation
- Jaeger trace visualization
- End-to-end request tracing across microservices

---

# Performance Testing

Performance testing is performed using **k6**.

The tests evaluate:

- Concurrent users
- Request latency
- Throughput
- Error rate
- System stability

During testing, Prometheus, Grafana, Loki, and Jaeger monitor the application's behavior in real time.

---

# Project Structure

```
janocare/
│
├── frontend/
│
├── backend/
│   ├── auth-service/
│   ├── professional-service/
│   ├── booking-service/
│   ├── payment-service/
│   └── notification-service/
│
├── gateway/
│
├── observability/
│   ├── prometheus/
│   ├── grafana/
│   ├── loki/
│   ├── promtail/
│   └── jaeger/
│
├── docker-compose.yml
│
└── README.md
```

---

# Running the Project

Clone the repository.

Start the complete platform.

```bash
docker compose up -d
```

The following services will be available:

| Service | URL |
|----------|-----|
| Frontend | http://localhost:4200 |
| API Gateway | http://localhost:8088 |
| Grafana | http://localhost:3001 |
| Prometheus | http://localhost:9090 |
| Jaeger | http://localhost:16686 |
| Adminer | http://localhost:8085 |

---

# Documentation

The project documentation includes:

- Software requirements analysis
- Use case analysis
- Domain model
- Database design
- Hexagonal architecture
- Application architecture
- Implementation details
- Observability architecture
- Performance testing
- Future developments

---

# Future Improvements

- Kubernetes deployment
- CI/CD pipeline
- Kafka event-driven communication
- OAuth2/OpenID Connect
- Mobile application
- Advanced monitoring and alerting
- Production deployment

---

# Author

**Michael Mogos**

Master's Student in Computer Science and Information Technology

Ca' Foscari University of Venice

Software Architecture for Web Applications and Middleware (SWAM)