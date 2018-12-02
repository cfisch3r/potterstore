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
        var price = PriceCalculation.price(new BigDecimal(8.22),Currency.getInstance("EUR"));
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
        assertPrice(price, 8.22, "EUR");

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
        assertPrice(price, 16.44, "EUR");
    }
    private void assertPrice(Price price, double amount, String currencyCode) {
        assertThat(price.getAmount()).isEqualTo(new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP));
        assertThat(price.getCurrency()).isEqualTo(Currency.getInstance(currencyCode));
    }


}
