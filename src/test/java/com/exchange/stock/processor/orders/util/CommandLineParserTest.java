package com.exchange.stock.processor.orders.util;

import com.exchange.stock.processor.orders.BaseTest;
import com.exchange.stock.processor.orders.error.OrderProcessorException;
import com.exchange.stock.processor.orders.model.Order;
import com.exchange.stock.processor.orders.validator.OrderValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandLineParserTest extends BaseTest {

    private OrderValidator orderValidator;

    private CommandLineParser commandLineParser;

    @BeforeEach
    void setup() {
        this.orderValidator = new OrderValidator();
        this.commandLineParser = new CommandLineParser(orderValidator);
    }


    @Test
    public void parseOrderSucceeds() {
        Order order = commandLineParser.parseOrder("#1 09:45 BAC sell 100 240.10");
        assertEquals("#1", order.getOrderId());
        assertEquals("09:45", order.getOrderTime().toString());
        assertEquals("BAC", order.getCompany().getSymbol().name());
        assertEquals("sell".toUpperCase(), order.getOrderType().name());
        assertEquals(100, order.getQuantity());
    }

    @Test
    @SneakyThrows
    public void parseOrderThrowsException() {
        assertThrows(OrderProcessorException.class, () -> {
            commandLineParser.parseOrder("#1 09:45 BAC sell 100");
        });
    }


}

