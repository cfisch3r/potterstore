package de.agiledojo.potterstore;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class PotterPriceCalculationTest {

    @Test
    public void singlePriceForOneBook() {
        PriceCalculation calculation = PriceCalculation.create(new Price(new BigDecimal(8)));
        var bookId = new BookId(){
            @Override
            public String getId() {
                return "book1";
            }
        };
        var price = calculation.priceFor(List.of(bookId));
        Assertions.assertThat(price.amount).isEqualTo(new BigDecimal(8));
    }
}
