package com.exchange.stock.processor.orders.handlers;

import com.exchange.stock.processor.orders.model.OrderType;
import org.springframework.stereotype.Component;

@Component
public class OrdersProcessorFactory {

    public OrdersProcessor getOrderProcessor(OrderType type) {
        switch (type) {
            case BUY:
                return new BuyOrdersProcessor();
            case SELL:
                return new SellOrdersProcessor();
        }

        // TODO prajakta: throw exception;

        return null;
    }
}
