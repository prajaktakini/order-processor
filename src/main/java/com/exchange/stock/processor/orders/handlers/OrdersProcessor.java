package com.exchange.stock.processor.orders.handlers;

import com.exchange.stock.processor.orders.model.StockOrder;

import java.util.List;

public abstract class OrdersProcessor {

    public abstract void processOrders(List<StockOrder> orderList);


}
