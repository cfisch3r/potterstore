package de.agiledojo.potterstore.app;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="potter")
public class PotterStoreConfigurationProperties {

    private Double singleBookPrice;

    private String currencyCode;

    private String dbConnectionString;

    private String dbUser;

    private String dbPassword;

    public Double getSingleBookPrice() {
        return singleBookPrice;
    }

    public void setSingleBookPrice(Double singleBookPrice) {
        this.singleBookPrice = singleBookPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDbConnectionString() {
        return dbConnectionString;
    }

    public void setDbConnectionString(String dbConnectionString) {
        this.dbConnectionString = dbConnectionString;
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
