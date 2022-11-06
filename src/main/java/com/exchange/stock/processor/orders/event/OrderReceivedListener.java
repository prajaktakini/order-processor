package com.exchange.stock.processor.orders.event;

import com.exchange.stock.processor.orders.handlers.OrderManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderReceivedListener {

    private OrderManager orderManager;

    @Autowired
    public OrderReceivedListener(final OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @EventListener
    public void onApplicationEvent(final OrderEvent event) {
        log.info("Received order event {}", event);
        orderManager.sendEvent(event);
    }
}
