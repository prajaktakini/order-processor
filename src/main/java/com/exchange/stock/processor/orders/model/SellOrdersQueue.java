package com.exchange.stock.processor.orders.model;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SellOrdersQueue {

    private PriorityQueue<StockOrder> sellQueue;

    public SellOrdersQueue() {
        this.sellQueue = new PriorityQueue<StockOrder>(getComparator());
    }

    public PriorityQueue<StockOrder> getOrders() {
        return sellQueue;
    }

    public void addQueue(StockOrder order) {
        sellQueue.add(order);
    }


    private Comparator getComparator() {
        return new Comparator<StockOrder>() {
            @Override
            public int compare(StockOrder order1, StockOrder order2) {
                // Ascending order comparator
                int compare = order1.getBiddingPrice().compareTo(order2.getBiddingPrice());

                if (compare == 0) {
                    if(order1.getOrderTime().isBefore(order2.getOrderTime())) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    return compare;
                }
            }
        };
    }
}
