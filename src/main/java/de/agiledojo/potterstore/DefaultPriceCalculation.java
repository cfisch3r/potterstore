package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.util.List;

class DefaultPriceCalculation implements PriceCalculation {
    private Price defaultSinglePrice;
    private ParameterRepository parameterRepository;

    public DefaultPriceCalculation(Price defaultSinglePrice, ParameterRepository parameterRepository) {
        this.defaultSinglePrice = defaultSinglePrice;
        this.parameterRepository = parameterRepository;
    }

    @Override
    public Price priceFor(List<BookId> bookIds) {
        Price singleBookPrice = getSingleBookPrice();
        BigDecimal totalAmount = singleBookPrice.getAmount().multiply(new BigDecimal(bookIds.size()));
        return PriceCalculation.price(totalAmount,singleBookPrice.getCurrency());
    }

    private Price getSingleBookPrice() {
            return parameterRepository.getSingleBookPrice().orElse(defaultSinglePrice);
    }
}
