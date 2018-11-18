package de.agiledojo.potterstore;

import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.domzal.junit.docker.rule.DockerRule;
import pl.domzal.junit.docker.rule.WaitFor;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,classes = PotterStore.class)
@TestPropertySource(properties = { "potter.single-book-price=8" })
public class PotterStoreSystemTest {

    @ClassRule
    public static DockerRule mysqlContainer = DockerRule.builder()
            .imageName("nginx")
//            .imageName("mysql:latest")
//            .expose("3306","3306")
//            .env("MYSQL_ROOT_PASSWORD","secret")
//            .waitFor(WaitFor.logMessage("MySQL init process done. Ready for start up."))
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
                .contentType(ContentType.JSON)
                .body(new PriceRequest("book1").json())
        .when()
                .post("/price")
        .then().assertThat()
                .statusCode(200)
                .body("amount", response -> equalTo(8))
                .body("currency", value -> equalTo("€"));
    }
}
