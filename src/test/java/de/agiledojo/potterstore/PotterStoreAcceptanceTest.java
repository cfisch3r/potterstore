package de.agiledojo.potterstore;

import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.domzal.junit.docker.rule.DockerRule;
import pl.domzal.junit.docker.rule.WaitFor;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,classes = PotterStore.class)
@TestPropertySource(properties = { "potter.single-book-price=8",
        "potter.db-connection-string=jdbc:mysql://localhost:3306/potter",
        "potter.db-user=potter",
        "potter.db-password=secret"})
public class PotterStoreAcceptanceTest {

    @ClassRule
    public static DockerRule mysqlContainer =
            MysqlDockerContainer.create("3306","potter","potter","secret");


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
                .contentType(JSON)
                .body(new PriceRequest("book1").json())
        .when()
                .post("/price")
        .then().assertThat()
                .statusCode(OK.value())
                .body("amount", response -> equalTo(8))
                .body("currency", value -> equalTo("€"));
    }


}
