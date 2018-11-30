package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Optional;

public class MysqlParameterRepository implements ParameterRepository {
    private ParameterCRUDRepository crudRepository;

    public MysqlParameterRepository(ParameterCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }


    @Override
    public void saveOrUpdateSingleBookPrice(BookPrice price) {
        crudRepository.save(new ParameterRecord("price",
                price.getAmount() +" " + price.getCurrency().getCurrencyCode()));

    }

    @Override
    public Optional<BookPrice> getSingleBookPrice() {
        Optional<ParameterRecord> parameterRecord = crudRepository.findById(1l);
        if (parameterRecord.isPresent()) {
            String price = parameterRecord.get().getParamValue();
            String[] parts = price.split(" ");
            var bookPrice = new BookPrice(){
                @Override
                public BigDecimal getAmount() {
                    return BigDecimal.valueOf(Double.valueOf(parts[0]).doubleValue()).setScale(2, RoundingMode.HALF_UP);
                }

                @Override
                public Currency getCurrency() {
                    return Currency.getInstance(parts[1]);
                }
            };
            return Optional.of(bookPrice);
        } else {
            return Optional.empty();
        }
    }
}
