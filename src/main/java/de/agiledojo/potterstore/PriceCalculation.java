package de.agiledojo.potterstore;

import java.util.List;

public interface PriceCalculation {

    static PriceCalculation create(Price singleBookPrice, ParameterRepository parameterRepository) {
        return new PriceCalculationImpl(singleBookPrice,parameterRepository);
    }

    Price priceFor(List<BookId> bookIds);
}
