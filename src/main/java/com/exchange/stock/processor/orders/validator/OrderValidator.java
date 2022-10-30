package com.exchange.stock.processor.orders.validator;

import com.exchange.stock.processor.orders.error.ErrorDetailBuilder;
import com.exchange.stock.processor.orders.error.OrderProcessorErrorCode;
import com.exchange.stock.processor.orders.error.OrderProcessorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;

@Component
@Slf4j
public class OrderValidator {

    private static int EXPECTED_TOKEN_LEN = 6;

    public void validateTokenCount(final String orderString,
                                   final int count) {
        if (count != EXPECTED_TOKEN_LEN) {
            throw new OrderProcessorException(
                    ErrorDetailBuilder.errorBuilder(OrderProcessorErrorCode.INPUT_INVALID_ERROR)
                            .withDetail(String.format("No. of args in input line %s does not match expected count %s", orderString,
                                    EXPECTED_TOKEN_LEN)).build())
                    .withHttpStatus(HttpStatus.BAD_REQUEST)
                    .logError(log);
        }
    }

    public void validateTokens(final String orderString, final LinkedHashSet<String> tokens) {
        tokens.forEach(token -> {
            if (StringUtils.isBlank(token)) {
                throw new OrderProcessorException(
                        ErrorDetailBuilder.errorBuilder(OrderProcessorErrorCode.TOKEN_EMPTY_OR_BLANK_ERROR)
                                .withDetail(String.format("Order %s contains one or more empty/blank tokens", orderString)).build())
                        .withHttpStatus(HttpStatus.BAD_REQUEST)
                        .logError(log);
            }
        });
    }
}
