package de.agiledojo.potterstore;

import java.util.Optional;

public interface ParameterRepository {

    void saveOrUpdateSingleBookPrice(Price price);

    Optional<Price> getSingleBookPrice();
}