package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.util.Currency;

public interface Price {

    BigDecimal getAmount();

    Currency getCurrency();
}
