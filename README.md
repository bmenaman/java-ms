# Example Java Microservice with Kafka Integration

TODO: git add Code is almost entirely written by AI.  Needs review, especially the DI and kafka configuration.

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


## Development

Build and test locally:
```bash
./gradlew build
```

Run tests:
```bash
./gradlew test
```

Generate mutation test coverage report:
```bash
./gradlew pitest
```
The mutation test report will be generated in `build/reports/pitest/`.

Format code:
```bash
./gradlew spotlessApply
```