package com.exchange.stock.processor.orders.kafka;

import com.exchange.stock.processor.orders.event.OrderEvent;
import com.exchange.stock.processor.orders.model.Company.Symbol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {

    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_PREFIX = "order.events.";

    public KafkaProducerService(final KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(OrderEvent event) {
        log.info("Sending Kafka event {}", event);
        this.kafkaTemplate.send(getTopic(event.getCompany().getSymbol()), event);
        log.info("Successfully sent event");
    }
    
    private String getTopic(Symbol companySymbol) {
        return TOPIC_PREFIX + companySymbol.name();
    }
}
