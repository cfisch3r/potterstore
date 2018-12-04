package de.agiledojo.potterstore.app.price;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = PriceControllerIntegrationTest.TestConfiguration.class)
public class PriceControllerIntegrationTest {

    @Configuration
    @EnableAutoConfiguration
//    @ComponentScan(basePackageClasses = PriceController.class)
    public static class TestConfiguration {
    }



    @Autowired
    private MockMvc mvc;

    @Test
    public void nothing() {
    }
}
