package com.exchange.stock.processor.orders.error;

public enum OrderProcessorErrorCode {

    INPUT_INVALID_ERROR("Provided input is invalid"),

    TOKEN_EMPTY_OR_BLANK_ERROR("Token is empty or blank"),

    INVALID_ORDER_TYPE("Invalid order type. Valid types are BUY or SELL");
    private final String message;

    OrderProcessorErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
