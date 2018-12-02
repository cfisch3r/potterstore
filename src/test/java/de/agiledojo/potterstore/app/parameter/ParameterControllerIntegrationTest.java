package de.agiledojo.potterstore.app.parameter;

import de.agiledojo.potterstore.ParameterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = ParameterControllerIntegrationTest.TestConfiguration.class)
public class ParameterControllerIntegrationTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackageClasses = ParameterController.class)
    public static class TestConfiguration {
    }


    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParameterRepository repository;

    @Test
    public void putDefaultPrice() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/parameters/{id}","price")
                .contentType(MediaType.APPLICATION_JSON).content("{\"amount\":3,\n\"currency\":\"EUR\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void putDefaultPriceSaveSPriceInRepository() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/parameters/{id}","price")
                .contentType(MediaType.APPLICATION_JSON).content("{\"amount\":3.45,\n\"currency\":\"EUR\"}"));
        Mockito.verify(repository).saveOrUpdateSingleBookPrice(ArgumentMatchers.argThat(price ->
                price.getAmount().compareTo(new BigDecimal(3.45).setScale(2, RoundingMode.HALF_UP)) == 0 &&
                price.getCurrency().equals(Currency.getInstance("EUR"))));
    }
}
