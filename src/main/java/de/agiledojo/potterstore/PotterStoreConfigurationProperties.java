package de.agiledojo.potterstore;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="potter")
public class PotterStoreConfigurationProperties {

    private double singleBookPrice;

    private String DbConnectionString;

    private String dbUser;

    private String dbPassword;

    public double getSingleBookPrice() {
        return singleBookPrice;
    }

    public void setSingleBookPrice(double singleBookPrice) {
        this.singleBookPrice = singleBookPrice;
    }

    public String getDbConnectionString() {
        return DbConnectionString;
    }

    public void setDbConnectionString(String dbConnectionString) {
        DbConnectionString = dbConnectionString;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
