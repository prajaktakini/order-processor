package com.exchange.stock.processor.orders.handlers;

import com.exchange.stock.processor.orders.error.ErrorDetailBuilder;
import com.exchange.stock.processor.orders.error.OrderProcessorErrorCode;
import com.exchange.stock.processor.orders.error.OrderProcessorException;
import com.exchange.stock.processor.orders.model.BuyOrdersQueue;
import com.exchange.stock.processor.orders.model.OrderEntry;
import com.exchange.stock.processor.orders.model.OrderType;
import com.exchange.stock.processor.orders.model.SellOrdersQueue;
import com.exchange.stock.processor.orders.model.StockCompany.Symbol;
import com.exchange.stock.processor.orders.model.StockOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
@Slf4j
public class OrderManager {

    @Autowired
    private OrdersProcessorFactory ordersProcessorFactory;

    private Map<String, BuyOrdersQueue> buys = new HashMap<>();

    private Map<String, SellOrdersQueue> sells = new HashMap<>();

    private List<OrderEntry> transactionList = new ArrayList<>();

    public void segregateOrdersForProcessing(List<StockOrder> stockOrderList) {
        OrdersProcessor buyOrderProcessor = ordersProcessorFactory.getOrderProcessor(OrderType.BUY);
        OrdersProcessor sellOrderProcessor = ordersProcessorFactory.getOrderProcessor(OrderType.SELL);

        if (CollectionUtils.isEmpty(stockOrderList)) {
            log.warn("Stock order list is empty, Returning");
            return;
        }

        List<StockOrder> buyOrders = new ArrayList<>();
        List<StockOrder> sellOrders = new ArrayList<>();

        for (StockOrder order : stockOrderList) {
            if (OrderType.BUY.equals(order.getOrderType())) {
                buyOrders.add(order);
            } else if (OrderType.SELL.equals(order.getOrderType())) {
                sellOrders.add(order);
            } else {
                throw new OrderProcessorException(
                        ErrorDetailBuilder.errorBuilder(OrderProcessorErrorCode.INVALID_ORDER_TYPE)
                                .withDetail(String.format("Order type %s in invalid. Valid orders are BUY or SELL", order.getOrderType())).build())
                        .withHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .logError(log);
            }
        }

        // Process buy orders
        buyOrderProcessor.processOrders(buyOrders);
        // Process sell orders
        sellOrderProcessor.processOrders(sellOrders);

    }

    public void addOrders(List<StockOrder> orders) {
        if (orders == null || orders.isEmpty()) {
            return;
        }

        for (StockOrder order : orders) {
            if (order == null) {
                continue;
            }

            if (order.getOrderType() == OrderType.BUY) {
                BuyOrdersQueue buyOrders = buys.get(order.getStockCompany().getSymbol().name());
                if (buyOrders == null) {
                    buyOrders = new BuyOrdersQueue();
                    buys.put(order.getStockCompany().getSymbol().name(), buyOrders);
                }

                buyOrders.addQueue(order);
                buys.put(order.getStockCompany().getSymbol().name(), buyOrders);

            } else if (order.getOrderType() == OrderType.SELL) {
                SellOrdersQueue sellOrders = sells.get(order.getStockCompany().getSymbol().name());
                if (sellOrders == null) {
                    sellOrders = new SellOrdersQueue();
                    sells.put(order.getStockCompany().getSymbol().name(), sellOrders);
                }

                sellOrders.addQueue(order);
                sells.put(order.getStockCompany().getSymbol().name(), sellOrders);
            }
        }
    }

    public List<OrderEntry> processOrders() {
        if (buys == null || buys.isEmpty() || sells == null || sells.isEmpty()) {
            return transactionList;
        }

        // process buy orders
        buys.forEach((stock, orders) -> {
            PriorityQueue<StockOrder> buyOrderSet = orders.getOrders();

            if (buyOrderSet == null || buyOrderSet.isEmpty()) {
                return;
            }

            SellOrdersQueue sOrderSet = sells.get(stock);
            if (sOrderSet == null) {
                return;
            }

            PriorityQueue<StockOrder> sellOrderSet = sOrderSet.getOrders();

            buyOrderSet.stream().filter(order -> (order.getQuantity() > 0)).forEach((buy) -> {
                for (StockOrder sell : sellOrderSet) {
                    if (sell.getQuantity() > 0 && buy.getBiddingPrice().compareTo(sell.getBiddingPrice()) >= 0) {

                        int qty = 0;
                        if (sell.getQuantity() > buy.getQuantity()) {
                            qty = buy.getQuantity();
                            sell.setQuantity(sell.getQuantity() - buy.getQuantity());
                            buy.setQuantity(0);
                        } else {
                            qty = sell.getQuantity();
                            buy.setQuantity(buy.getQuantity() - sell.getQuantity());
                            sell.setQuantity(0);
                        }

                        // record it in order entry
                        OrderEntry entry =
                                new OrderEntry(sell, buy, qty, sell.getBiddingPrice());
                        transactionList.add(entry);
                    }
                }
            });
        });

        return transactionList;
    }



}
