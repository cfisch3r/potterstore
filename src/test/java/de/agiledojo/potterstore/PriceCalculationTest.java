package de.agiledojo.potterstore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static de.agiledojo.potterstore.PriceCalculation.bookId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PriceCalculationTest {

    @Mock
    private ParameterRepository parameterRepository;

    private PriceCalculation priceCalculation;


    @Before
    public void setUp() {
        var price = PriceCalculation.price(new BigDecimal(8.00f),Currency.getInstance("EUR"));
        priceCalculation = PriceCalculation.create(price,parameterRepository);
    }

    @Test
    public void createPrice() {
        var price = PriceCalculation.price(new BigDecimal(4.56),Currency.getInstance("EUR"));
        assertPrice(price, 4.56, "EUR");
    }

    @Test
    public void createBookId() {
        var bookId = bookId("book1");
        assertThat(bookId.getId()).isEqualTo("book1");
    }

    @Test
    public void defaultSinglePrice() {
        var price = priceCalculation.priceFor(List.of(bookId("book1")));
        assertPrice(price, 8.00f, "EUR");

    }

    @Test
    public void singlePriceFromRepository() {
        var bookPrice = PriceCalculation.price(new BigDecimal(3.95),Currency.getInstance("EUR"));
        when(parameterRepository.getSingleBookPrice()).thenReturn(Optional.of(bookPrice));
        var price = priceCalculation.priceFor(List.of(bookId("book1")));
        assertPrice(price, 3.95, "EUR");
    }

    @Test
    public void emptyBookIdListReturnsZeroPrice() {
        var price = priceCalculation.priceFor(Collections.emptyList());
        assertPrice(price, 0.00f, "EUR");
    }

    @Test
    public void priceForMultipleBooksWithSameId() {
        var bookId = bookId("book1");
        var price = priceCalculation.priceFor(List.of(bookId,bookId));
        assertPrice(price, 16.00f, "EUR");
    }
    @Test
    public void two_different_books_get_discount() {
        var price = priceCalculation.priceFor(List.of(bookId("book1"),bookId("book2")));
        assertPrice(price, (2*8*0.95));
    }

    @Test
    public void three_different_books_get_discount() {
        var price = priceCalculation.priceFor(List.of(bookId("book1"),
                bookId("book2"),
                bookId("book3")));
        assertPrice(price, (3*8*0.9));
    }

    @Test
    public void four_different_books_get_discount() {
        var price = priceCalculation.priceFor(List.of(bookId("book1"),
                bookId("book2"),
                bookId("book3"),
                bookId("book4")));
        assertPrice(price, (4*8*0.8));
    }

    @Test
    public void five_different_books_get_discount() {
        var price = priceCalculation.priceFor(List.of(bookId("book1"),
                bookId("book2"),
                bookId("book3"),
                bookId("book4"),
                bookId("book5")));
        assertPrice(price, (5*8*0.75));
    }

    @Test
    public void multiple_book_series() {
        var price = priceCalculation.priceFor(List.of(bookId("book1"),
                bookId("book1"),
                bookId("book2"),
                bookId("book2")));
        assertPrice(price, (2*2*8*0.95));
    }

    @Test
    public void complex_calculation() {
        var price = priceCalculation.priceFor(List.of(bookId("book1"),
                bookId("book3"),
                bookId("book2"),
                bookId("book3"),
                bookId("book3"),
                bookId("book2")));
        assertPrice(price, (3*8*0.9 + 2*8*0.95 +8 ));
    }


    private void assertPrice(Price price, double amount, String currencyCode) {
        assertThat(price.getAmount()).isEqualTo(new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP));
        assertThat(price.getCurrency()).isEqualTo(Currency.getInstance(currencyCode));
    }

    private void assertPrice(Price price, double amount) {
        assertPrice(price,amount,"EUR");
    }

}
