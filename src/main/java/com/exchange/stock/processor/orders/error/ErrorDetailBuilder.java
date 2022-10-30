package com.exchange.stock.processor.orders.error;

public class ErrorDetailBuilder {

    private final ErrorDetail errorDetail;

    private ErrorDetailBuilder() {
        // Should never be instantiated
        this.errorDetail = new ErrorDetail();
    }


    public static ErrorDetailBuilder errorBuilder(final OrderProcessorErrorCode errorCode) {
        ErrorDetailBuilder builder = new ErrorDetailBuilder();
        builder.errorDetail.setDetail(errorCode.getMessage());
        builder.errorDetail.setCode(errorCode.name());
        builder.errorDetail.setMessage(errorCode.getMessage());
        return builder;
    }

    public ErrorDetailBuilder withDetail(String detail) {
        errorDetail.setDetail(detail);
        return this;
    }

    public ErrorDetail build() {
        return errorDetail;
    }

}
