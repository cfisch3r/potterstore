package de.agiledojo.potterstore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public void priceForMultipleBooksWithSameId() {
        var bookId = bookId("book1");
        var price = priceCalculation.priceFor(List.of(bookId,bookId));
        assertPrice(price, 16.00f, "EUR");
    }

    private void assertPrice(Price price, double amount, String currencyCode) {
        assertThat(price.getAmount()).isEqualTo(new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP));
        assertThat(price.getCurrency()).isEqualTo(Currency.getInstance(currencyCode));
    }

    private void assertPrice(Price price, double amount) {
        assertPrice(price,amount,"EUR");
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

    //    @Test
//    public void two_different_and_one_identical_book_get_combined_price() {
//        var price = calculator.priceFor(asList(BOOK1, BOOK2, BOOK1));
//        Assert.assertEquals(new Price(8*2*0.95+8),price);
//    }
//
//    @Test
//    public void two_different_and_one_identical_book_different_order_get_combined_price() {
//        var price = calculator.priceFor(asList(BOOK1, BOOK1, BOOK2));
//        Assert.assertEquals(new Price(8*2*0.95+8),price);
//    }
//
//    @Test
//    public void three_books_from_series_get_discount() {
//        var price = calculator.priceFor(asList(BOOK1, BOOK2, BOOK3));
//        Assert.assertEquals(new Price(8*3*0.9),price);
//    }
//
//    @Test
//    public void four_books_from_series_get_discount() {
//        var price = calculator.priceFor(asList(BOOK1, BOOK2, BOOK3, BOOK4));
//        Assert.assertEquals(new Price(8*4*0.8),price);
//    }
//
//    @Test
//    public void five_books_from_series_get_discount() {
//        var price = calculator.priceFor(asList(BOOK1, BOOK2, BOOK3, BOOK4, BOOK5));
//        Assert.assertEquals(new Price(8*5*0.75),price);
//    }

}
