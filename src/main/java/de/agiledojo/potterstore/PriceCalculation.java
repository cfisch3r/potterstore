package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface PriceCalculation {

    static BookId bookId(String id) {
        return new BookId() {
            @Override
            public String getId() {
                return id;
            }
        };
    }

    static Price price(BigDecimal amount, Currency currency) {
        return new Price() {
            @Override
            public BigDecimal getAmount() {
                return amount;
            }

            @Override
            public Currency getCurrency() {
                return currency;
            }
        };
    }

    static PriceCalculation create(Price defaultPrice) {
        return new DefaultPriceCalculation(defaultPrice);
    }

    Price priceFor(List<BookId> bookId);

}
