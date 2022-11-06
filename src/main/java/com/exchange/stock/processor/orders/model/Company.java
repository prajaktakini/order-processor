package com.exchange.stock.processor.orders.model;

import lombok.Data;


@Data
public class Company {
    private Symbol symbol;

    public Company(final Symbol symbol) {
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
