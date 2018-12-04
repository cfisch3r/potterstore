package de.agiledojo.potterstore;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculationTest {


    @Test
    public void createBookId() {
        BookId bookId = PriceCalculation.bookId("book1");
        assertThat(bookId.getId()).isEqualTo("book1");
    }

    @Test
    public void createPrice() {
        Price price = PriceCalculation.price(new BigDecimal(8.45), Currency.getInstance("EUR"));
        assertSamePrice(8.45, price);
    }

    @Test
    public void defaultPriceForOneBook() {
        var defaultPrice = PriceCalculation.price(new BigDecimal(8),Currency.getInstance("EUR"));
        PriceCalculation priceCalculation = PriceCalculation.create(defaultPrice);
        var price = priceCalculation.priceFor(List.of(PriceCalculation.bookId("book1")));
        assertSamePrice(8, price);
    }

    private void assertSamePrice(double expectedAmount, Price actualPrice) {
        assertThat(actualPrice.getAmount()).isEqualTo(new BigDecimal(expectedAmount));
        assertThat(actualPrice.getCurrency()).isEqualTo(Currency.getInstance("EUR"));
    }


}
