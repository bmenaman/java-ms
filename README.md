# Java Microservice with Kafka Integration

A Spring Boot microservice that processes numbers via Kafka and maintains a running total.

## Prerequisites

- Java 21 (OpenJDK)
- Docker
- Gradle (optional, wrapper included)

## Quick Start

1. Build the container:
```bash
./gradlew jibDockerBuild
```

2. Run the container:
```bash
docker run -d -p 8080:8080 --name bmenaman-ms bmenaman-ms:latest
```

3. Verify the health endpoint:
```bash
./scripts/health-check.sh
```

The script will poll the `/actuator/health` endpoint and exit with:
- Status code 0 if the endpoint returns `{"status":"UP"}`
- Status code 1 after 30 retries if the endpoint is not healthy

## Development

Build and test locally:
```bash
./gradlew build
```

Run tests:
```bash
./gradlew test
```

Run mutation tests:
```bash
./gradlew pitest
```

Format code:
```bash
./gradlew spotlessApply
``` 