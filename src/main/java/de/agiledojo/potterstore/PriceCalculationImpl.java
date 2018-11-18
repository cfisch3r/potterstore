package de.agiledojo.potterstore;

import java.util.List;

class PriceCalculationImpl implements PriceCalculation {
    private Price singleBookPrice;

    public PriceCalculationImpl(Price singleBookPrice) {
        this.singleBookPrice = singleBookPrice;
    }

    @Override
    public Price priceFor(List<BookId> bookIds) {
        return singleBookPrice;
    }
}
