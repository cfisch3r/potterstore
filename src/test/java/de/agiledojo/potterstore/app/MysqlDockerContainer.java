package de.agiledojo.potterstore.app;

import pl.domzal.junit.docker.rule.DockerRule;
import pl.domzal.junit.docker.rule.WaitFor;

public class MysqlDockerContainer {

    public static DockerRule create(String hostPort, String dbName, String userName, String password) {
        return DockerRule.builder()
                .imageName("mysql:latest")
                .expose(hostPort,"3306")
                .env("MYSQL_ROOT_PASSWORD","geheim")
                .env("MYSQL_USER", userName)
                .env("MYSQL_PASSWORD", password)
                .env("MYSQL_DATABASE", dbName)
                .waitFor(WaitFor.logMessage("bind-address: '::' port: 33060"))
                .build();
    }
}
