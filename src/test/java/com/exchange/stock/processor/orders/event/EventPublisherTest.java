package com.exchange.stock.processor.orders.event;

import com.exchange.stock.processor.orders.BaseTest;
import com.exchange.stock.processor.orders.kafka.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EventPublisherTest extends BaseTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private OrderPublisher orderPublisher;

    @SpyBean
    private KafkaProducerService kafkaProducerService;

    @SpyBean
    private KafkaTemplate kafkaTemplate;


    @Test
    public void orderApplicationEventPublishedSuccess() {
        orderPublisher.publishEvent(constructOrderEvent());
        verify(kafkaProducerService, times(1)).sendMessage(any());
        verify(kafkaTemplate, times(1)).send(any(), any());
    }
}
