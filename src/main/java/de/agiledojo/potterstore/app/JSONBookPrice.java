package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.BookPrice;

import java.math.BigDecimal;
import java.util.Currency;

public class JSONBookPrice implements BookPrice {

    public BigDecimal amount;

    public String currency;

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public Currency getCurrency() {
        return Currency.getInstance(currency);
    }
}
