package com.exchange.stock.processor.orders.model;

import lombok.Data;

import java.util.UUID;

@Data
public class StockCompany {
    private String stockId;
    private Symbol symbol;

    public StockCompany(final Symbol symbol) {
        this.stockId = UUID.randomUUID().toString();
        this.symbol = symbol;
    }

    public enum Symbol {
        BAC("BANK OF AMERICA"),
        TCS("TATA CONSULTANCY SERVICES");

        private String companyName;

        Symbol(final String companyName) {
            this.companyName = companyName;
        }

        @Override
        public String toString() {
            return "Symbol{" +
                    "companyName='" + companyName + '\'' +
                    '}';
        }
    }


}
