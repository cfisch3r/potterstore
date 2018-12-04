package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.PriceCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Currency;

@SpringBootApplication
@EnableConfigurationProperties(PotterStoreConfigurationProperties.class)
public class PotterStoreApplication {

    @Bean
    PriceCalculation priceCalculation(@Autowired PotterStoreConfigurationProperties properties) {
        var defaultPrice = PriceCalculation.price(new BigDecimal(properties.getSingleBookPrice().doubleValue()),
                Currency.getInstance(properties.getCurrencyCode()));
        return PriceCalculation.create(defaultPrice);
    }

    public static void main(String[] args) {
        SpringApplication.run(PotterStoreApplication.class, args);
    }
}
