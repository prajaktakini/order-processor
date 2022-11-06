package com.exchange.stock.processor.orders.event;

import com.exchange.stock.processor.orders.model.OrderType;
import com.exchange.stock.processor.orders.model.Company;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class OrderEvent {

    @NonNull
    private String orderId;

    @NonNull
    private LocalTime orderTime;

    @NonNull
    private OrderType orderType;

    private int quantity;

    @NonNull
    private Company company;

    @NonNull
    private BigDecimal price;

}
