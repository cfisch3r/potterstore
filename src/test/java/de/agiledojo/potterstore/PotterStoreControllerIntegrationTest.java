package de.agiledojo.potterstore;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PotterStoreController.class)
public class PotterStoreControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PriceCalculation priceCalculation;

    @Before
    public void setUp() throws Exception {
        when(priceCalculation.priceFor(ArgumentMatchers.anyList()))
                .thenReturn(new Price(new BigDecimal(8)));
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
        verify(priceCalculation).priceFor(eq(List.of(new BookId("book1"))));
    }

    private ResultActions sendRequestForPrice(String bookId) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post("/price").accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON).content(jsonMessage(bookId)));
    }

    private String jsonMessage(String bookId) {
        return "[{\"id\": \""+ bookId +"\"}]";
    }
}
