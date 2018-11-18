package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.util.Currency;

public interface BookPrice {

    BigDecimal getAmount();

    Currency getCurrency();
}
