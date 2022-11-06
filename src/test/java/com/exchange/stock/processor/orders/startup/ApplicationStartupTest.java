package com.exchange.stock.processor.orders.startup;

import com.exchange.stock.processor.orders.BaseTest;
import com.exchange.stock.processor.orders.util.CommandLineParser;
import com.exchange.stock.processor.orders.validator.OrderValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.io.BufferedReader;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= ApplicationStartupTest.ApplicationStartupConfig.class)
@TestExecutionListeners(listeners = {ApplicationStartupTest.ApplicationStartupConfig.class})
@ActiveProfiles("MockUnitTest")
@AutoConfigureMockMvc
@SpringBootTest
public class ApplicationStartupTest extends BaseTest {

    @SpyBean
    OrderValidator orderValidator;

    @SpyBean
    private CommandLineParser mockCommandLineParser;


    @SneakyThrows
    public void test1() {
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine()).thenReturn("#1 09:45 BAC sell 100 240.10");


        verify(mockCommandLineParser, times(1)).parseOrder(any());

    }

    @Configuration
    public static class ApplicationStartupConfig extends AbstractTestExecutionListener {

        @Override
        public void beforeTestClass(TestContext testContext) throws Exception {
            System.out.println("Testing...");
            ApplicationContext context = testContext.getApplicationContext(); //Do anything you want here
            ApplicationReadyEvent applicationReadyEvent = mock(ApplicationReadyEvent.class);
            context.publishEvent(applicationReadyEvent);
        }
    }
}
