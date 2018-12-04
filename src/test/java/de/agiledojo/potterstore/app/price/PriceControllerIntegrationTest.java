package de.agiledojo.potterstore.app.price;

import de.agiledojo.potterstore.BookId;
import de.agiledojo.potterstore.Price;
import de.agiledojo.potterstore.PriceCalculation;
import de.agiledojo.potterstore.app.PriceRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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

    @MockBean
    private PriceCalculation priceCalculation;


    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp(){
        var price = new Price() {

            @Override
            public BigDecimal getAmount() {
                return new BigDecimal(8.45);
            }

            @Override
            public Currency getCurrency() {
                return Currency.getInstance("EUR");
            }
        };
        when(priceCalculation.priceFor(ArgumentMatchers.anyList())).thenReturn(price);
    }

    @Test
    public void validPriceRequestHasResponseStatusOK() throws Exception {
        sendPriceRequest()
                .andExpect(status().isOk());
    }

    @Test
    public void sendsCalculatedPriceAsJSONResponse() throws Exception {
        sendPriceRequest()
                .andExpect(jsonPath("$.amount", is(8.45)))
                .andExpect(jsonPath("$.currency", is("€")));
    }

    @Test
    public void calculatesPriceForRequestedBooks() throws Exception {
        sendPriceRequest();
        ArgumentCaptor<List<BookId>> captor = ArgumentCaptor.forClass(List.class);
        verify(priceCalculation).priceFor(captor.capture());
        Assertions.assertThat(captor.getValue().get(0).getId()).isEqualTo("book1");
    }

    private ResultActions sendPriceRequest() throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                .post("/price")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(new PriceRequest("book1").json()));
    }
}
