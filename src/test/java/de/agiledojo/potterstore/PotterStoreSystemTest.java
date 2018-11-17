package de.agiledojo.potterstore;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,classes = PotterStore.class)
public class PotterStoreSystemTest {

    @LocalServerPort
    int port;

    @Test
    public void applicationListensToAPort() {
        Assertions.assertThat(port).isNotEqualTo(0);
    }

    @Test
    public void singlePriceIsReturnedForOneBook() {
        given()
                .port(port)
                .body(new PriceRequest("book1").json())
        .when()
                .post("/price")
        .then().assertThat()
                .body("amount", response -> equalTo(8))
                .body("currency", value -> equalTo("€"));
    }
}
