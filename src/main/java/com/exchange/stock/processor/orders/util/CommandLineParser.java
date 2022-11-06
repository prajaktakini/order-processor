package com.exchange.stock.processor.orders.util;

import com.exchange.stock.processor.orders.model.OrderType;
import com.exchange.stock.processor.orders.model.Company;
import com.exchange.stock.processor.orders.model.Company.Symbol;
import com.exchange.stock.processor.orders.model.Order;
import com.exchange.stock.processor.orders.validator.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

@Slf4j
@Component
public class CommandLineParser {

    private OrderValidator orderValidator;


    @Autowired
    public CommandLineParser(final OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
    }

    public Order parseOrder(String order) {

        StringTokenizer tokenizer = new StringTokenizer(order, " ");
        orderValidator.validateTokenCount(order, tokenizer.countTokens());

        List<String> tokens = new ArrayList<>();
        while(tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }

        // Validate if tokens are not empty/blank
        orderValidator.validateTokens(order, tokens);
        return buildStockOrder(tokens);
    }

    private Order buildStockOrder(List<String> tokens) {
        Iterator iterator = tokens.iterator();
        return Order.builder()
                .orderId(iterator.next().toString())
                .orderTime(LocalTime.parse(iterator.next().toString(), DateTimeFormatter.ofPattern("HH:mm",
                        Locale.getDefault())))
                .company(new Company(Symbol.valueOf(iterator.next().toString())))
                .orderType(OrderType.valueOf(iterator.next().toString().toUpperCase()))
                .quantity(Integer.parseInt(iterator.next().toString()))
                .price(new BigDecimal(iterator.next().toString()))
                .build();
    }
}
