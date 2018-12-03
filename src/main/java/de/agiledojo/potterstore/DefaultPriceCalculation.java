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
        BigDecimal totalAmount = totalAmount(bookIds, singleBookPrice.getAmount());
        return PriceCalculation.price(totalAmount,singleBookPrice.getCurrency());
    }

    private BigDecimal totalAmount(List<BookId> bookIds, BigDecimal singleBookPriceAmount) {
        var distinctBooks = bookIds.stream().distinct().count();
        BigDecimal totalAmount;
        if (distinctBooks > 1)
            totalAmount = singleBookPriceAmount.multiply(new BigDecimal(bookIds.size())).multiply(discount(distinctBooks));
        else
            totalAmount = singleBookPriceAmount.multiply(new BigDecimal(bookIds.size()));
        return totalAmount;
    }

    private BigDecimal discount(long seriesSize) {
        if (seriesSize  == 3)
            return new BigDecimal(0.9);
        else
            return new BigDecimal(0.95);
    }

    private Price getSingleBookPrice() {
            return parameterRepository.getSingleBookPrice().orElse(defaultSinglePrice);
    }
}
