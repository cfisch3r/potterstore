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
@TestPropertySource(properties = {"potter.single-book-price=5.43" })
public class PotterStoreConfigurationTest {

    @TestConfiguration
    @EnableConfigurationProperties(PotterStoreConfiguration.class)
    static class MyTestConfiguration {

    }

    @Autowired
    PotterStoreConfiguration configuration;

    @Test
    public void price() {
        Assertions.assertThat(configuration.getSingleBookPrice()).isEqualTo(5.43);
    }
}
