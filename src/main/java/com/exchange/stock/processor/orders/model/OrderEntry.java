package com.exchange.stock.processor.orders.model;


import java.math.BigDecimal;
import java.util.UUID;

public class OrderEntry {
    private final UUID id;
    private final StockOrder party;
    private final StockOrder counterParty;
    private final int quantity;
    private final BigDecimal executionPrice;

    public OrderEntry(StockOrder party, StockOrder counterParty, int quantity, BigDecimal executionPrice) {
        this.id = UUID.randomUUID();
        this.party = party;
        this.counterParty = counterParty;
        this.quantity = quantity;
        this.executionPrice = executionPrice;
    }

    public UUID getId() {
        return id;
    }

    public StockOrder getParty() {
        return this.party;
    }

    public StockOrder getCounterParty() {
        return this.counterParty;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public BigDecimal getExecutionPrice() {
        return this.executionPrice;
    }
}
