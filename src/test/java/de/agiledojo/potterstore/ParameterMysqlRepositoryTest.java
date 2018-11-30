package de.agiledojo.potterstore;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.domzal.junit.docker.rule.DockerRule;
import pl.domzal.junit.docker.rule.WaitFor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ParameterRepositoryConfiguration.class)
public class ParameterMysqlRepositoryTest {

    @MockBean
    private PotterStoreConfigurationProperties properties;

    @Autowired
    private ParameterRepository repository;


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


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void noPriceWhenNothingWasSaved() {
        assertThat(repository.getSingleBookPrice()).isEmpty();
    }

    @Test
    public void getSavedPrice() {
        repository.saveOrUpdateSingleBookPrice(new BookPrice() {
            @Override
            public BigDecimal getAmount() {
                return new BigDecimal(12);
            }

            @Override
            public Currency getCurrency() {
                return Currency.getInstance("EUR");
            }
        });

        var price = repository.getSingleBookPrice().get();
        assertThat(price.getAmount()).isEqualTo(new BigDecimal(12).setScale(2, RoundingMode.HALF_UP));
        assertThat(price.getCurrency().getCurrencyCode()).isEqualTo("EUR");
    }
}
