package de.agiledojo.potterstore;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PriceCalculationTest {

    @Mock
    private ParameterRepository parameterRepository;

    private PriceCalculation priceCalculation;


    @Before
    public void setUp() {
        priceCalculation = PriceCalculation.create(new Price(new BigDecimal(8.22)),parameterRepository);
    }

    @Test
    public void defaultSinglePrice() {
        Price price = priceCalculation.priceFor(List.of(new BookId() {
            @Override
            public String getId() {
                return "book1";
            }
        }));
        Assertions.assertThat(price.amount).isEqualTo(new BigDecimal(8.22));
    }

    @Test
    public void singlePriceFromRepository() {
        var bookPrice = new BookPrice(){

            @Override
            public BigDecimal getAmount() {
                return new BigDecimal(3.95);
            }

            @Override
            public Currency getCurrency() {
                return Currency.getInstance("EUR");
            }
        };
        when(parameterRepository.getSingleBookPrice()).thenReturn(Optional.of(bookPrice));
        var price = priceCalculation.priceFor(List.of(new BookId() {
            @Override
            public String getId() {
                return "book1";
            }
        }));
        Assertions.assertThat(price.amount).isEqualTo(new BigDecimal(3.95));
    }

}
