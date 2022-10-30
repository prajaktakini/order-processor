package com.exchange.stock.processor.orders.controller;

import com.exchange.stock.processor.orders.error.ErrorDetailBuilder;
import com.exchange.stock.processor.orders.error.OrderProcessorErrorCode;
import com.exchange.stock.processor.orders.error.OrderProcessorException;
import com.exchange.stock.processor.orders.handlers.OrderManager;
import com.exchange.stock.processor.orders.model.OrderEntry;
import com.exchange.stock.processor.orders.model.StockOrder;
import com.exchange.stock.processor.orders.util.CommandLineParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CommandLineRunnerController {

    private CommandLineParser commandLineParser;

    private OrderManager orderManager;

    private static String END_OF_INPUT_KEY = "execute";

    @Autowired
    public CommandLineRunnerController(final CommandLineParser commandLineParser, final OrderManager orderManager) {
        this.commandLineParser = commandLineParser;
        this.orderManager = orderManager;
    }

    public void loadArgs() {
        System.out.println("Enter new orders on each line in the format:<order-id> <time> <stock> <buy/sell> <qty> <price>. " +
                "Enter 'execute' when done");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<StockOrder> orderList = new ArrayList<>();
        try {
            String orderString = null;
            while (!(orderString = bufferedReader.readLine()).equals(END_OF_INPUT_KEY)) {
                orderList.add(commandLineParser.parseOrder(orderString));
                //orderManager.segregateOrdersForProcessing(orderList);
            }
            orderManager.addOrders(orderList);
            writeToCLI(orderManager.processOrders());
        } catch (IOException e) {
            throw new OrderProcessorException(
                    ErrorDetailBuilder.errorBuilder(OrderProcessorErrorCode.INPUT_INVALID_ERROR).build())
                    .withHttpStatus(HttpStatus.BAD_REQUEST)
                    .logError(log);
        }
    }

    public void writeToCLI(List<OrderEntry> entries) {
        entries.forEach((entry) -> {
            String output = String.format("%s %d %.2f %s", entry.getParty().getOrderId(), entry.getQuantity(),
                    entry.getExecutionPrice(), entry.getCounterParty().getOrderId());
            System.out.println(output);
        });
    }

}
