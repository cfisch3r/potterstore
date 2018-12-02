package de.agiledojo.potterstore.app;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.domzal.junit.docker.rule.DockerRule;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,classes = PotterStoreApplication.class)
@TestPropertySource(properties = { "potter.single-book-price=8.32",
        "potter.currency-code=EUR",
        "potter.db-connection-string=jdbc:mysql://localhost:3306/potter",
        "potter.db-user=potter",
        "potter.db-password=secret"})
public class CalculatingPricesAcceptanceTest {

    @ClassRule
    public static DockerRule mysqlContainer =
            MysqlDockerContainer.create("3306","potter","potter","secret");


    @LocalServerPort
    int port;


    @Test
    public void defaultPriceIsReturnedForOneBook() {
        given()
                .port(port)
                .contentType(JSON)
                .body(new PriceRequest("book1").json())
        .when()
                .post("/price")
        .then().assertThat()
                .statusCode(OK.value())
                .body("amount", response -> equalTo(8.32f))
                .body("currency", value -> equalTo("€"));
    }

    @Test
    public void calculatedPriceIsReturnedForListOfBooks() {
        given()
                .port(port)
                .contentType(JSON)
                .body(new PriceRequest(List.of("book1","book1")).json())
                .when()
                .post("/price")
                .then().assertThat()
                .statusCode(OK.value())
                .body("amount", response -> equalTo(16.64f))
                .body("currency", value -> equalTo("€"));
    }

}
