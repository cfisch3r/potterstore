package de.agiledojo.potterstore;

import java.util.List;

class DefaultPriceCalculation implements PriceCalculation {
    private Price singleBookPrice;
    private ParameterRepository parameterRepository;

    public DefaultPriceCalculation(Price singleBookPrice, ParameterRepository parameterRepository) {
        this.singleBookPrice = singleBookPrice;
        this.parameterRepository = parameterRepository;
    }

    @Override
    public Price priceFor(List<BookId> bookIds) {
        if (parameterRepository.getSingleBookPrice().isPresent())
            return new Price(parameterRepository.getSingleBookPrice().get().getAmount());
        else
            return singleBookPrice;
    }
}
