package com.exchange.stock.processor.orders.kafka;

import com.exchange.stock.processor.orders.BaseTest;
import com.exchange.stock.processor.orders.event.OrderEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestKafkaOrderEvent extends BaseTest {
    @Mock
    private KafkaTemplate kafkaTemplate;

    private KafkaProducerService kafkaProducerService;

    @Test
    void testPublishOrderEvent() {
        kafkaProducerService = new KafkaProducerService(kafkaTemplate);

        OrderEvent orderEvent = constructOrderEvent();
        kafkaProducerService.sendMessage(orderEvent);

        verify(kafkaTemplate, times(1)).send(any(), any());
    }


}
