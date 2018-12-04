package de.agiledojo.potterstore;

import java.util.List;

class DefaultPriceCalculation implements PriceCalculation {
    private Price defaultPrice;

    public DefaultPriceCalculation(Price defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    @Override
    public Price priceFor(List<BookId> bookId) {
        return defaultPrice;
    }
}
