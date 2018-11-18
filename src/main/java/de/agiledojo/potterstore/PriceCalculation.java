package de.agiledojo.potterstore;

import java.util.List;

public interface PriceCalculation {

    static PriceCalculation create(Price singleBookPrice) {
        return new PriceCalculationImpl(singleBookPrice);
    }

    Price priceFor(List<BookId> bookIds);
}
