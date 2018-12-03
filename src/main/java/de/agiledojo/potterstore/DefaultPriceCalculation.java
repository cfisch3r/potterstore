package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.util.HashMap;
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
        return singleBookPriceAmount.multiply(new BigDecimal(bookIds.size())).multiply(discount(distinctBooks));
    }

    private BigDecimal discount(long seriesSize) {
        var discounts = new HashMap<Long,Double>() {
            {
                put(1l,1d);
                put(2l,0.95);
                put(3l,0.9);
                put(4l,0.8);
                put(5l,0.75);
            }
        };
        return new BigDecimal(discounts.get(seriesSize));
    }

    private Price getSingleBookPrice() {
            return parameterRepository.getSingleBookPrice().orElse(defaultSinglePrice);
    }
}
