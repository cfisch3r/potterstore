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
        if (bookIds.size() == 0)
            return new BigDecimal(0);

        var distinctBooks = distinctBooks(bookIds);
        var amount = seriesPrice(singleBookPriceAmount, distinctBooks.size());
        return amount.add(totalAmount(substract(bookIds, distinctBooks),singleBookPriceAmount));

    }

    private List<BookId> distinctBooks(List<BookId> bookIds) {
        return bookIds.stream().filter(distinctByKey(id -> id.getId())).collect(Collectors.toList());
    }

    private BigDecimal seriesPrice(BigDecimal singleBookPriceAmount, int numberOfDistinctBooks) {
        return singleBookPriceAmount.multiply(new BigDecimal(numberOfDistinctBooks))
                .multiply(discount(numberOfDistinctBooks));
    }

    private ArrayList<BookId> substract(List<BookId> bookIds, List<BookId> distinctBooks) {
        var remainingBooks = new ArrayList<BookId>(bookIds);
        for (var bookId : distinctBooks) {
            remainingBooks.remove(bookId);
        }
        return remainingBooks;
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
