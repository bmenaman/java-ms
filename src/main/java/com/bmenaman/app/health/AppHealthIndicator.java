package com.bmenaman.app.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AppHealthIndicator implements HealthIndicator {

  @Override
  public Health health() {
    return Health.up()
        .withDetail("version", "0.1.0")
        .withDetail("description", "Java Microservice with Kafka Integration")
        .build();
  }
}
