package com.exchange.stock.processor.orders;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class OrdersProcessingApplication{

	public static void main(String[] args) {
		log.info("---Orders processing application started---");
		SpringApplication.run(OrdersProcessingApplication.class, args);
	}
}
