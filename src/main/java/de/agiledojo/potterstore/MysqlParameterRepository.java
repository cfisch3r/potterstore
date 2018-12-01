package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Optional;

public class MysqlParameterRepository implements ParameterRepository {

    private enum PARAMETER_KEYS {
        PRICE;
    }

    private ParameterCRUDRepository crudRepository;

    public MysqlParameterRepository(ParameterCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }


    @Override
    public void saveOrUpdateSingleBookPrice(BookPrice price) {
        crudRepository.save(new ParameterRecord(PARAMETER_KEYS.PRICE.toString(),
                price.getAmount() +" " + price.getCurrency().getCurrencyCode()));

    }

    @Override
    public Optional<BookPrice> getSingleBookPrice() {
        var records = crudRepository.findByParamKey(PARAMETER_KEYS.PRICE.toString());
        if (records.size() == 1) {
            return Optional.of(parameterToBookPrice(records.get(0)));
        } else {
            return Optional.empty();
        }
    }

    private BookPrice parameterToBookPrice(ParameterRecord parameterRecord) {
        String price = parameterRecord.getParamValue();
        String[] parts = price.split(" ");
        return new BookPrice(){
            @Override
            public BigDecimal getAmount() {
                return BigDecimal.valueOf(Double.valueOf(parts[0]).doubleValue())
                        .setScale(2, RoundingMode.HALF_UP);
            }

            @Override
            public Currency getCurrency() {
                return Currency.getInstance(parts[1]);
            }
        };
    }
}
