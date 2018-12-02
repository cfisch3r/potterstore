package de.agiledojo.potterstore.app.price;

import de.agiledojo.potterstore.Price;
import de.agiledojo.potterstore.PriceCalculation;
import de.agiledojo.potterstore.app.PriceRequest;
import de.agiledojo.potterstore.app.parameter.ParameterController;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Currency;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = PriceControllerIntegrationTest.TestConfiguration.class)
public class PriceControllerIntegrationTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackageClasses = PriceController.class)
    public static class TestConfiguration {
    }



    @Autowired
    private MockMvc mvc;

    @MockBean
    private PriceCalculation priceCalculation;

    @Before
    public void setUp() throws Exception {
        setCalculatedPrice(8);
    }

    private void setCalculatedPrice(int amount) {
        var price = new Price(){

            @Override
            public BigDecimal getAmount() {
                return new BigDecimal(amount);
            }

            @Override
            public Currency getCurrency() {
                return Currency.getInstance("EUR");
            }
        };
        when(priceCalculation.priceFor(ArgumentMatchers.anyList()))
                .thenReturn(price);
    }

    @Test
    public void mvcExists() {
        Assertions.assertThat(mvc).isNotNull();
    }

    @Test
    public void responseHasStatusCodeOK() throws Exception {
        sendRequestForPrice("book1")
                .andExpect(status().isOk());
    }

    @Test
    public void calculatedPriceIsSentAsJSON() throws Exception {
        sendRequestForPrice("book1")
                .andExpect(jsonPath("$.amount", is(8)))
                .andExpect(jsonPath("$.currency", is("€")));
    }


    @Test
    public void idFromRequestIsPassedToPriceCalculation() throws Exception {
        sendRequestForPrice("book1");
        verify(priceCalculation).priceFor(argThat(bookIds ->
                bookIds.size() == 1 && bookIds.get(0).getId().equals("book1")));
    }

    private ResultActions sendRequestForPrice(String bookId) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post("/price").accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON).content(new PriceRequest(bookId).json()));
    }

}
