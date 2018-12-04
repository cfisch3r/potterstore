package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DefaultPriceCalculation implements PriceCalculation {

    private static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> ke) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(ke.apply(t), Boolean.TRUE) == null;
    }

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
        var amount = new BigDecimal(0);
        if (bookIds.size() == 0)
            return amount;
        else {
            var distinctBooks = bookIds.stream().filter(distinctByKey(id -> id.getId())).collect(Collectors.toList());
            var numberOfdistinctBooks = distinctBooks.size();
            amount = amount.add(singleBookPriceAmount.multiply(new BigDecimal(numberOfdistinctBooks)).multiply(discount(numberOfdistinctBooks)));
            var remainingBooks = new ArrayList<BookId>(bookIds);
            for (var bookId : distinctBooks) {
                remainingBooks.remove(bookId);
            }
            return amount.add(totalAmount(remainingBooks,singleBookPriceAmount));
        }
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
