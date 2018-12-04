package de.agiledojo.potterstore.app.price;

import de.agiledojo.potterstore.Price;

import java.math.BigDecimal;
import java.util.Currency;

public class JSONPrice {

    private Double amount;

    private String currency;
    private Price price;

    public JSONPrice(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public JSONPrice(Price price) {

        this.price = price;
    }

    public Double getAmount() {
        return price.getAmount().doubleValue();
    }

    public String getCurrency() {
        return price.getCurrency().getSymbol();
    }

}
