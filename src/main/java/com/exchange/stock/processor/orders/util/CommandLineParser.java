package com.exchange.stock.processor.orders.util;

import com.exchange.stock.processor.orders.model.OrderType;
import com.exchange.stock.processor.orders.model.StockCompany;
import com.exchange.stock.processor.orders.model.StockCompany.Symbol;
import com.exchange.stock.processor.orders.model.StockOrder;
import com.exchange.stock.processor.orders.validator.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.StringTokenizer;

@Component
@Slf4j
public class CommandLineParser {

    private OrderValidator orderValidator;


    @Autowired
    public CommandLineParser(final OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
    }

    public StockOrder parseOrder(String order) {

        StringTokenizer tokenizer = new StringTokenizer(order, " ");
        orderValidator.validateTokenCount(order, tokenizer.countTokens());

        LinkedHashSet<String> tokens = new LinkedHashSet<>();
        while(tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }

        // Validate if tokens are not empty/blank
        orderValidator.validateTokens(order, tokens);
        StockOrder stockOrder = buildStockOrder(tokens);
        //log.info("Constructed order {} from order string", stockOrder);
        return stockOrder;
    }

    private StockOrder buildStockOrder(LinkedHashSet<String> tokens) {
        Iterator iterator = tokens.iterator();
        return StockOrder.builder()
                .orderId(iterator.next().toString())
                .orderTime(LocalTime.parse(iterator.next().toString(), DateTimeFormatter.ofPattern("HH:mm",
                        Locale.getDefault())))
                .stockCompany(new StockCompany(Symbol.valueOf(iterator.next().toString())))
                .orderType(OrderType.valueOf(iterator.next().toString().toUpperCase()))
                .quantity(Integer.parseInt(iterator.next().toString()))
                .biddingPrice(new BigDecimal(iterator.next().toString()))
                .build();
    }
}
