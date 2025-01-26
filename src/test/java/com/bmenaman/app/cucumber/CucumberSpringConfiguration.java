package com.bmenaman.app.cucumber;

import com.bmenaman.app.Application;
import com.bmenaman.app.config.KafkaConfig;
import com.bmenaman.app.cucumber.config.TestKafkaConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(
    classes = {Application.class, TestKafkaConfig.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(
    basePackages = "com.bmenaman.app",
    excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = KafkaConfig.class))
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    topics = {"numbers", "totals"},
    bootstrapServersProperty = "spring.kafka.bootstrap-servers")
@TestPropertySource(properties = {"spring.kafka.consumer.auto-offset-reset=earliest"})
public class CucumberSpringConfiguration {}
