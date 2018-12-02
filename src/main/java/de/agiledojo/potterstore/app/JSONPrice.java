package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.Price;

import java.math.BigDecimal;

public class JSONPrice {

    private BigDecimal amount;

    private String currency;

    public JSONPrice() {
    }

    public JSONPrice(Price price) {
        this.amount = price.getAmount();
        this.currency = price.getCurrency().getSymbol();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }



}
