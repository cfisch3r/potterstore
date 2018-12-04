package de.agiledojo.potterstore.app.repository;

import de.agiledojo.potterstore.app.MysqlDockerContainer;
import de.agiledojo.potterstore.app.PotterStoreConfigurationProperties;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.domzal.junit.docker.rule.DockerRule;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ParameterRepositoryIntegrationTest.TestConfiguration.class)
@Transactional
public class ParameterRepositoryIntegrationTest {

        @Configuration
        @EnableAutoConfiguration
        @Import(ParameterRepositoryConfiguration.class)
        public static class TestConfiguration {

            @Bean
            PotterStoreConfigurationProperties properties() {
                var properties = new PotterStoreConfigurationProperties();
                properties.setDbConnectionString("jdbc:mysql://localhost:3308/potter");
                properties.setDbUser("potter");
                properties.setDbPassword("secret");
                return properties;
            }
        }


        @ClassRule
        public static DockerRule mysqlContainer =
                MysqlDockerContainer.create("3308","potter","potter","secret");



        @Test
        public void nothing() {
        }
}
