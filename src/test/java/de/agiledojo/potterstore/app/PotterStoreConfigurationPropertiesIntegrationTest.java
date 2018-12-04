package de.agiledojo.potterstore.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "potter.single-book-price=5.43",
        "potter.currency-code=EUR"})
public class PotterStoreConfigurationPropertiesIntegrationTest {

    @Configuration
//    @EnableConfigurationProperties(PotterStoreConfigurationProperties.class)
    static class TestConfiguration {

    }

//    @Autowired
//    PotterStoreConfigurationProperties configuration;


    @Test
    public void nothing() {
    }
}
