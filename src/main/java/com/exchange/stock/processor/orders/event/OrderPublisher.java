package com.exchange.stock.processor.orders.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderPublisher {

    private final ApplicationEventPublisher publisher;

    public OrderPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(final OrderEvent orderEvent) {
        log.info("Publishing order event {}", orderEvent);

        // Publishing event created by extending ApplicationEvent
        publisher.publishEvent(orderEvent);
    }
}
