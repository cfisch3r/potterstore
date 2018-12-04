package de.agiledojo.potterstore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
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
    public void setUp() throws Exception {
        var defaultPrice = PriceCalculation.price(new BigDecimal(8),Currency.getInstance("EUR"));
        priceCalculation = PriceCalculation.create(defaultPrice,parameterRepository);
    }

    @Test
    public void createBookId() {
        BookId bookId = bookId("book1");
        assertThat(bookId.getId()).isEqualTo("book1");
    }

    @Test
    public void createPrice() {
        Price price = PriceCalculation.price(new BigDecimal(8.45), Currency.getInstance("EUR"));
        assertSamePrice(8.45, price);
    }

    @Test
    public void defaultPriceForOneBook() {
        var price = priceCalculation.priceFor(List.of(bookId("book1")));
        assertSamePrice(8, price);
    }

    @Test
    public void singlePriceFromRepository() {
        var bookPrice = PriceCalculation.price(new BigDecimal(3.95),Currency.getInstance("EUR"));
        when(parameterRepository.getSingleBookPrice()).thenReturn(Optional.of(bookPrice));
        var price = priceCalculation.priceFor(List.of(bookId("book1")));
        assertSamePrice(3.95, price);
    }



    private void assertSamePrice(double expectedAmount, Price actualPrice) {
        assertThat(actualPrice.getAmount()).isEqualTo(new BigDecimal(expectedAmount));
        assertThat(actualPrice.getCurrency()).isEqualTo(Currency.getInstance("EUR"));
    }


}
