package de.agiledojo.potterstore;

import java.util.List;

public interface PriceCalculation {

    Price priceFor(List<BookId> bookIds);
}
