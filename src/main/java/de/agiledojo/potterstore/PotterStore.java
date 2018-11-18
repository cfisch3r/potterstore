package de.agiledojo.potterstore;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableConfigurationProperties(PotterStoreConfiguration.class)
public class PotterStore {

   @Bean
   public PriceCalculation priceCalculation (PotterStoreConfiguration configuration) {
       return PriceCalculation.create(new Price(new BigDecimal(configuration.getSingleBookPrice())));
   }
}
