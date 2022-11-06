package com.exchange.stock.processor.orders.startup;

import com.exchange.stock.processor.orders.error.ErrorDetailBuilder;
import com.exchange.stock.processor.orders.error.OrderProcessorErrorCode;
import com.exchange.stock.processor.orders.error.OrderProcessorException;
import com.exchange.stock.processor.orders.event.OrderPublisher;
import com.exchange.stock.processor.orders.mapper.OrderMapper;
import com.exchange.stock.processor.orders.util.CommandLineParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private OrderPublisher orderPublisher;

    private CommandLineParser commandLineParser;

    private OrderMapper orderMapper;

    public ApplicationStartup(final OrderPublisher orderPublisher,
                              final CommandLineParser commandLineParser,
                              final OrderMapper orderMapper) {
        this.orderPublisher = orderPublisher;
        this.commandLineParser = commandLineParser;
        this.orderMapper = orderMapper;
    }


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("Received application ready event");
        loadArgs();
    }

    public void loadArgs() {
        System.out.println("Enter new orders on each line in the format:<order-id> <time> <stock> <buy/sell> <qty> <price>.");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            try {
                String orderString = bufferedReader.readLine();
                orderPublisher.publishEvent(orderMapper.toOrderEvent(commandLineParser.parseOrder(orderString)));

            } catch (IOException e) {
                // TODO: Prajakta update below behaviour
                throw new OrderProcessorException(
                        ErrorDetailBuilder.errorBuilder(OrderProcessorErrorCode.INPUT_INVALID_ERROR).build())
                        .withHttpStatus(HttpStatus.BAD_REQUEST)
                        .logError(log);
            } catch (OrderProcessorException ex) {
                log.error("Error occurred: ", ex);
            }
        }
    }
}
