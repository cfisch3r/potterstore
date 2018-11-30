package de.agiledojo.potterstore;

import java.util.Optional;

public interface ParameterRepository {

    void saveOrUpdateSingleBookPrice(BookPrice price);

    Optional<BookPrice> getSingleBookPrice();
}
