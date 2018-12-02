package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.ParameterRepository;
import de.agiledojo.potterstore.Price;
import de.agiledojo.potterstore.PriceCalculation;
import de.agiledojo.potterstore.app.repository.ParameterRepositoryConfiguration;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.domzal.junit.docker.rule.DockerRule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ParameterRepositoryIntegrationTest.MyTestConfiguration.class, ParameterRepositoryConfiguration.class})
@Transactional
public class ParameterRepositoryIntegrationTest {

    @TestConfiguration
    public static class MyTestConfiguration {

        @Bean
        PotterStoreConfigurationProperties properties() {
            var properties = new PotterStoreConfigurationProperties();
            properties.setDbConnectionString("jdbc:mysql://localhost:3308/potter");
            properties.setDbUser("potter");
            properties.setDbPassword("secret");
            return properties;
        }
    }

    @Autowired
    private ParameterRepository repository;


    @ClassRule
    public static DockerRule mysqlContainer =
            MysqlDockerContainer.create("3308","potter","potter","secret");



    @Test
    public void noPriceWhenNothingWasSaved() {
        assertThat(repository.getSingleBookPrice()).isEmpty();
    }

    @Test
    public void getSavedPrice() {
        repository.saveOrUpdateSingleBookPrice(price(12.43, "EUR"));

        var price = repository.getSingleBookPrice().get();
        assertThat(price.getAmount()).isEqualTo(new BigDecimal(12.43).setScale(2, RoundingMode.HALF_UP));
        assertThat(price.getCurrency().getCurrencyCode()).isEqualTo("EUR");
    }

    @Test
    public void getUpdatedPrice() {
        repository.saveOrUpdateSingleBookPrice(price(13.22,"EUR"));
        repository.saveOrUpdateSingleBookPrice(price(16.23,"EUR"));

        var price = repository.getSingleBookPrice().get();
        assertThat(price.getAmount()).isEqualTo(new BigDecimal(16.23).setScale(2, RoundingMode.HALF_UP));
        assertThat(price.getCurrency().getCurrencyCode()).isEqualTo("EUR");
    }

    private Price price(double amount, String currencyCode) {
        return PriceCalculation.price(new BigDecimal(amount), Currency.getInstance(currencyCode));
    }

}
