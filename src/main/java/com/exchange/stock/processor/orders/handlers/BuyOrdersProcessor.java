package com.exchange.stock.processor.orders.handlers;

import com.exchange.stock.processor.orders.model.BuyOrdersQueue;
import com.exchange.stock.processor.orders.model.StockCompany.Symbol;
import com.exchange.stock.processor.orders.model.StockOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyOrdersProcessor extends OrdersProcessor {

    public Map<Symbol, BuyOrdersQueue> buyOrders;

    public BuyOrdersProcessor() {
        this.buyOrders = new HashMap<>();
    }

    public Map<Symbol, BuyOrdersQueue> getOrders() {
        return buyOrders;
    }

    @Override
    public void processOrders(final List<StockOrder> orderList) {
        // TODO Prajakta: Implement Kafka event processing
    }
}
