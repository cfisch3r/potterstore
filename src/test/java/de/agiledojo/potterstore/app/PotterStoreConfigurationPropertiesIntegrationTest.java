package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.app.PotterStoreConfigurationProperties;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"potter.single-book-price=5.43",
        "potter.currency-code=EUR",
        "potter.db-connection-string=jdbc:mysql://127.0.0.1/potterstore",
        "potter.db-user=potter",
        "potter.db-password=secret"})
public class PotterStoreConfigurationPropertiesIntegrationTest {

    @TestConfiguration
    @EnableConfigurationProperties(PotterStoreConfigurationProperties.class)
    static class MyTestConfiguration {

    }

    @Autowired
    PotterStoreConfigurationProperties configuration;

    @Test
    public void price() {
        assertThat(configuration.getSingleBookPrice()).isEqualTo(5.43);
    }

    @Test
    public void connectionString() {
        assertThat(configuration.getDbConnectionString()).isEqualTo("jdbc:mysql://127.0.0.1/potterstore");
    }

    @Test
    public void dbUser() {
        assertThat(configuration.getDbUser()).isEqualTo("potter");
    }

    @Test
    public void dbPassword() {
        assertThat(configuration.getDbPassword()).isEqualTo("secret");
    }

    @Test
    public void currencyCode() {
        assertThat(configuration.getCurrencyCode()).isEqualTo("EUR");
    }
}
