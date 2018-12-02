package de.agiledojo.potterstore;

import java.util.List;

class DefaultPriceCalculation implements PriceCalculation {
    private Price singlePrice;
    private ParameterRepository parameterRepository;

    public DefaultPriceCalculation(Price singlePrice, ParameterRepository parameterRepository) {
        this.singlePrice = singlePrice;
        this.parameterRepository = parameterRepository;
    }

    @Override
    public Price priceFor(List<BookId> bookIds) {
        if (parameterRepository.getSingleBookPrice().isPresent())
            return parameterRepository.getSingleBookPrice().get();
        else
            return singlePrice;
    }
}
