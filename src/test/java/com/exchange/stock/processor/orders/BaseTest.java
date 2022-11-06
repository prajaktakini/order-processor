package com.exchange.stock.processor.orders;

import com.exchange.stock.processor.orders.event.OrderEvent;
import com.exchange.stock.processor.orders.model.Company;
import com.exchange.stock.processor.orders.model.Company.Symbol;
import com.exchange.stock.processor.orders.model.OrderType;
import com.exchange.stock.processor.orders.startup.ApplicationStartup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrdersProcessingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@ActiveProfiles("MockUnitTest")
@AutoConfigureMockMvc
public class BaseTest {

    @MockBean
    private ApplicationStartup applicationStartup;

    protected OrderEvent constructOrderEvent() {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId(UUID.randomUUID().toString());
        orderEvent.setOrderType(OrderType.BUY);
        orderEvent.setCompany(new Company(Symbol.BAC));
        orderEvent.setOrderTime(LocalTime.now());
        orderEvent.setQuantity(1);
        orderEvent.setPrice(new BigDecimal(123.4));
        return orderEvent;
    }
}
