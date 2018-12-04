package de.agiledojo.potterstore.app;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "potter.single-book-price=5.43",
        "potter.currency-code=EUR",
        "potter.db-connection-string=jdbc:mysql://localhost:3306/potter",
        "potter.db-user=potter",
        "potter.db-password=secret"})
public class PotterStoreConfigurationPropertiesIntegrationTest {

    @Configuration
    @EnableConfigurationProperties(PotterStoreConfigurationProperties.class)
    static class TestConfiguration {

    }

    @Autowired
    PotterStoreConfigurationProperties configuration;


    @Test
    public void providesSingleBookPrice() {
        Double singleBookPrice = configuration.getSingleBookPrice();
        Assertions.assertThat(singleBookPrice).isEqualTo(5.43d);
    }

    @Test
    public void providesCurrencyCode() {
        String currencyCode = configuration.getCurrencyCode();
        Assertions.assertThat(currencyCode).isEqualTo("EUR");
    }

    @Test
    public void providesDbConnectionString() {
        String currencyCode = configuration.getDbConnectionString();
        Assertions.assertThat(currencyCode).isEqualTo("jdbc:mysql://localhost:3306/potter");
    }

    @Test
    public void providesDbUser() {
        String currencyCode = configuration.getDbUser();
        Assertions.assertThat(currencyCode).isEqualTo("potter");
    }

    @Test
    public void providesDbPassword() {
        String currencyCode = configuration.getDbPassword();
        Assertions.assertThat(currencyCode).isEqualTo("secret");
    }
}
