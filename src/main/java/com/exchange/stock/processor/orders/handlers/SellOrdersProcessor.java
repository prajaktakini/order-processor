package com.exchange.stock.processor.orders.handlers;

import com.exchange.stock.processor.orders.model.StockCompany.Symbol;
import com.exchange.stock.processor.orders.model.StockOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class SellOrdersProcessor extends OrdersProcessor {

    private Map<Symbol, PriorityQueue<StockOrder>> sellOrders;

    public SellOrdersProcessor() {
        this.sellOrders = new HashMap<>();
    }

    public Map<Symbol, PriorityQueue<StockOrder>> getSellOrders() {
        return sellOrders;
    }

    @Override
    public void processOrders(final List<StockOrder> orderList) {
        // TODO Prajakta: Implement Kafka event processing
    }
}
