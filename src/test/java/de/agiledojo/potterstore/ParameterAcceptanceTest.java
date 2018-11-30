package de.agiledojo.potterstore;

import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import pl.domzal.junit.docker.rule.DockerRule;
import pl.domzal.junit.docker.rule.WaitFor;

import java.net.URI;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,classes = PotterStore.class)
public class ParameterAcceptanceTest {

    private static final String DB_PW = "secret";

    @ClassRule
    public static DockerRule mysqlContainer = DockerRule.builder()
            .imageName("mysql:latest")
            .expose("3306","3306")
            .env("MYSQL_ROOT_PASSWORD",DB_PW)
            .env("MYSQL_USER","potter")
            .env("MYSQL_PASSWORD","secret")
            .env("MYSQL_DATABASE","potter")
            .waitFor(WaitFor.logMessage("MySQL init process done. Ready for start up."))
            .build();

    @LocalServerPort
    int port;

    @Test
    public void createdSinglePriceIsReturnedForOneBook() {
        given()
                .port(port)
                .contentType(JSON)
                .body("{\"amount\":7.23,\n\"currency\":\"EUR\"}")
                .when()
                .put("/parameters/price")
                .then().assertThat()
                .statusCode(OK.value());

        given()
                .port(port)
                .contentType(JSON)
                .body(new PriceRequest("book1").json())
                .when()
                .post("/price")
                .then().assertThat()
                .statusCode(OK.value())
                .body("amount", (response) -> equalTo(7.23f))
                .body("currency", value -> equalTo("€"));

    }


}
