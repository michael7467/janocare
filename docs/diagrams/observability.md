```mermaid
flowchart TD
    MS[Quarkus Microservices] --> M[/q/metrics/]
    M --> PR[Prometheus]
    PR --> GR[Grafana Dashboards]

    MS --> OT[OpenTelemetry]
    OT --> J[Jaeger Tracing]

    MS --> DL[Docker Logs]
    DL --> PT[Promtail]
    PT --> L[Loki]
    L --> GR
```