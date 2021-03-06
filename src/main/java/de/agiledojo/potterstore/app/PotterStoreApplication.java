package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.ParameterRepository;
import de.agiledojo.potterstore.PriceCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Currency;

@SpringBootApplication
@EnableConfigurationProperties(PotterStoreConfigurationProperties.class)
public class PotterStoreApplication {

   @Bean
   public PriceCalculation priceCalculation (PotterStoreConfigurationProperties configuration, @Autowired ParameterRepository parameterRepository) {
       var price = PriceCalculation.price(new BigDecimal(configuration.getSingleBookPrice()),
               Currency.getInstance(configuration.getCurrencyCode()));
       return PriceCalculation.create(price, parameterRepository);
   }
}
