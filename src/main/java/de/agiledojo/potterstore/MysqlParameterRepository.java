package de.agiledojo.potterstore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
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
        var records = crudRepository.findByParamKey(PARAMETER_KEYS.PRICE.toString());
        var record = updateRecord(records,serialize(price));
        crudRepository.save(record);
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

    private ParameterRecord updateRecord(List<ParameterRecord> records, String value) {
        if (records.size() == 1) {
            var record = records.get(0);
            record.setParamValue(value);
            return record;
        } else {
            return new ParameterRecord(PARAMETER_KEYS.PRICE.toString(), value);
        }

    }

    private String serialize(BookPrice price) {
        return price.getAmount() +" " + price.getCurrency().getCurrencyCode();
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
