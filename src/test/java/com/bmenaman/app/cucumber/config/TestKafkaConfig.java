package com.bmenaman.app.cucumber.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

@TestConfiguration
public class TestKafkaConfig {

  @Value("${spring.embedded.kafka.brokers}")
  private String bootstrapServers;

  @Bean(name = "testProducerFactory")
  public ProducerFactory<String, Integer> producerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
    return new DefaultKafkaProducerFactory<>(props);
  }

  @Bean(name = "testConsumerFactory")
  public ConsumerFactory<String, Integer> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "cucumber-test-group");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean(name = "testKafkaTemplate")
  public KafkaTemplate<String, Integer> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean(name = "testKafkaListenerContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, Integer> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Integer> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}
