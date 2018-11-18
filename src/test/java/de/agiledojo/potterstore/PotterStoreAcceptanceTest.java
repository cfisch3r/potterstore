package de.agiledojo.potterstore;

import com.mongodb.MongoClient;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.domzal.junit.docker.rule.DockerRule;
import pl.domzal.junit.docker.rule.WaitFor;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,classes = PotterStore.class)
@TestPropertySource(properties = { "potter.single-book-price=8" })
public class PotterStoreAcceptanceTest {

    @ClassRule
    public static DockerRule mongoContainer = DockerRule.builder()
            .imageName("mongo:latest")
            .expose("27017","27017")
            .env("MONGO_INITDB_ROOT_USERNAME","root")
            .env("MONGO_INITDB_ROOT_PASSWORD","secret")
            .build();

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

    @Test
    public void createdSinglePriceIsReturnedForOneBook() {
        given()
                .port(port)
                .contentType(JSON)
                .body("{value:3.43}")
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
                .body("amount", response -> equalTo(3.43))
                .body("currency", value -> equalTo("€"));

    }


}
