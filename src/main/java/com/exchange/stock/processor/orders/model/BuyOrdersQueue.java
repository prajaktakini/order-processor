package com.exchange.stock.processor.orders.model;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BuyOrdersQueue {
    private PriorityQueue<StockOrder> buyQueue;

    public BuyOrdersQueue() {
        this.buyQueue = new PriorityQueue<StockOrder>(getComparator());
    }

    public PriorityQueue<StockOrder> getOrders() {
        return buyQueue;
    }

    public void addQueue(StockOrder order) {
        buyQueue.add(order);
    }


    private Comparator getComparator() {

        return new Comparator<StockOrder>() {
            @Override
            public int compare(StockOrder order1, StockOrder order2) {
                // Descending order comparator
                return order1.getOrderTime().compareTo(order2.getOrderTime());
               // return compare == true ? 1 : -1;
//                int compare = order2.getBiddingPrice().compareTo(order1.getBiddingPrice());
//
//                if (compare == 0) {
//                   if(order1.getOrderTime().isBefore(order2.getOrderTime())) {
//                       return 1;
//                   } else {
//                       return -1;
//                   }
//                } else {
//                    return compare;
//                }
            }
        };
    }
}


