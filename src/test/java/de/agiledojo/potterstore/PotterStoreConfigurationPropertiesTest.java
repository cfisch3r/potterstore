package de.agiledojo.potterstore;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"potter.single-book-price=5.43",
        "potter.db-connection-string=jdbc:mysql://127.0.0.1/potterstore",
        "potter.db-user=potter",
        "potter.db-password=secret"})
public class PotterStoreConfigurationPropertiesTest {

    @TestConfiguration
    @EnableConfigurationProperties(PotterStoreConfigurationProperties.class)
    static class MyTestConfiguration {

    }

    @Autowired
    PotterStoreConfigurationProperties configuration;

    @Test
    public void price() {
        Assertions.assertThat(configuration.getSingleBookPrice()).isEqualTo(5.43);
    }

    @Test
    public void connectionString() {
        Assertions.assertThat(configuration.getDbConnectionString()).isEqualTo("jdbc:mysql://127.0.0.1/potterstore");
    }

    @Test
    public void dbUser() {
        Assertions.assertThat(configuration.getDbUser()).isEqualTo("potter");
    }

    @Test
    public void dbPassword() {
        Assertions.assertThat(configuration.getDbPassword()).isEqualTo("secret");
    }
}
