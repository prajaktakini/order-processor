package com.exchange.stock.processor.orders.handlers;

import com.exchange.stock.processor.orders.event.OrderEvent;
import com.exchange.stock.processor.orders.kafka.KafkaProducerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


@Component
@Slf4j
public class OrderManager {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public void sendEvent(OrderEvent event) {
        kafkaProducerService.sendMessage(event);
    }

}
