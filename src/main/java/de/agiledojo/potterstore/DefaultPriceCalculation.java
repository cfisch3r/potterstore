package de.agiledojo.potterstore;

import java.util.List;

class DefaultPriceCalculation implements PriceCalculation {
    private Price defaultPrice;
    private ParameterRepository parameterRepository;

    public DefaultPriceCalculation(Price defaultPrice, ParameterRepository parameterRepository) {
        this.defaultPrice = defaultPrice;
        this.parameterRepository = parameterRepository;
    }

    @Override
    public Price priceFor(List<BookId> bookId) {
        return parameterRepository.getSingleBookPrice().orElse(defaultPrice);
    }
}
