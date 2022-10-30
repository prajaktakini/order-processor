package com.exchange.stock.processor.orders;

import com.exchange.stock.processor.orders.controller.CommandLineRunnerController;
import com.exchange.stock.processor.orders.error.OrderProcessorException;
import com.exchange.stock.processor.orders.handlers.OrderManager;
import com.exchange.stock.processor.orders.util.CommandLineParser;
import com.exchange.stock.processor.orders.validator.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@Slf4j
@SpringBootApplication
public class OrdersProcessingApplication implements CommandLineRunner {

	@Autowired
	private CommandLineRunnerController commandLineRunnerController;

	public static void main(String[] args) {
		log.info("---Orders processing application started---");
		SpringApplication.run(OrdersProcessingApplication.class, args);
	}


	@Override
	public void run(final String... args) throws OrderProcessorException {
		log.info("Executing command line runner");
		OrderValidator orderValidator = new OrderValidator();
		OrderManager orderManager = new OrderManager();
		CommandLineRunnerController commandLineRunnerController =
				new CommandLineRunnerController(new CommandLineParser(orderValidator), orderManager);
		commandLineRunnerController.loadArgs();
	}


}
