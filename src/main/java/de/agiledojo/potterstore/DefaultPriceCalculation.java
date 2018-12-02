package de.agiledojo.potterstore;

import java.math.BigDecimal;
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
        Price singleBookPrice = getSingleBookPrice();
        BigDecimal totalAmount = singleBookPrice.getAmount().multiply(new BigDecimal(bookIds.size()));
        var totalPrice = PriceCalculation.price(totalAmount,singleBookPrice.getCurrency());
        return totalPrice;
    }

    private Price getSingleBookPrice() {
        if (parameterRepository.getSingleBookPrice().isPresent())
            return parameterRepository.getSingleBookPrice().get();
        else
            return singlePrice;
    }
}
