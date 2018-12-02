package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.Price;
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
public class PotterStore {

   @Bean
   public PriceCalculation priceCalculation (PotterStoreConfigurationProperties configuration, @Autowired ParameterRepository parameterRepository) {
       var price = new Price(){

           @Override
           public BigDecimal getAmount() {
               return new BigDecimal(configuration.getSingleBookPrice());
           }

           @Override
           public Currency getCurrency() {
               return Currency.getInstance("EUR");
           }
       };
       return PriceCalculation.create(price, parameterRepository);
   }
}
