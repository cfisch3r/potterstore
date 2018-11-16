package de.agiledojo.potterstore;

import java.math.BigDecimal;

public class Price {

    public BigDecimal amount;

    public String currency = "€";

    public Price(BigDecimal amount) {
        this.amount = amount;
    }
}
