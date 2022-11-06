package com.exchange.stock.processor.orders.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerConfig {

    public static final String CONNECTIONS_MAX_IDLE_MS = "connections.max.idle.ms";

    private int connectionsMaxIdleMs;
    @Autowired
    private KafkaProperties kafkaProperties;

    public ProducerFactory<String, Object> eventProducerFactory() {
        Map<String, Object> configurations =
                new HashMap<>(kafkaProperties.buildProducerProperties());

        configurations
                .put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                        StringSerializer.class);
        configurations
                .put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        JsonSerializer.class);
        configurations.put(CONNECTIONS_MAX_IDLE_MS, connectionsMaxIdleMs);

        return new DefaultKafkaProducerFactory<>(configurations);
    }

    @Bean
    public KafkaTemplate<String, Object> eventKafkaTemplate() {
        return new KafkaTemplate<>(eventProducerFactory());
    }
}
