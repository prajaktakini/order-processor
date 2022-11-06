package com.exchange.stock.processor.orders.mapper;

import com.exchange.stock.processor.orders.event.OrderEvent;
import com.exchange.stock.processor.orders.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;


@Slf4j
@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    public abstract OrderEvent toOrderEvent(Order order);

}
