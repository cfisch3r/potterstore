package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;

public interface PriceCalculation {

    static PriceCalculation create(Price singlePrice, ParameterRepository parameterRepository) {
        return new DefaultPriceCalculation(singlePrice,parameterRepository);
    }

    static Price price(BigDecimal amount, Currency currency) {
        return new Price(){

            @Override
            public BigDecimal getAmount() {
                return amount.setScale(2, RoundingMode.HALF_UP);
            }

            @Override
            public Currency getCurrency() {
                return currency;
            }
        };
    }

    static BookId bookId(String title) {
        return new BookId() {
            @Override
            public String getId() {
                return title;
            }
        };
    }

    Price priceFor(List<BookId> bookIds);
}
