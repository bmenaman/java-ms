package com.bmenaman.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NumberProcessingService {
  private static final Logger log = LoggerFactory.getLogger(NumberProcessingService.class);
  private final KafkaTemplate<String, Integer> kafkaTemplate;
  private int runningTotal = 0;

  public NumberProcessingService(KafkaTemplate<String, Integer> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = "numbers", groupId = "${spring.kafka.consumer.group-id}")
  public void processNumber(Integer number) {
    log.info("Received number: {}", number);
    runningTotal += number;
    log.info("New running total: {}", runningTotal);

    kafkaTemplate
        .send("totals", runningTotal)
        .whenComplete(
            (result, ex) -> {
              if (ex != null) {
                log.error("Failed to publish total: {}", ex.getMessage(), ex);
              } else {
                log.info("Published total: {}", runningTotal);
              }
            });
  }
}
