package de.agiledojo.potterstore;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="potter")
public class PotterStoreConfigurationProperties {

    private double singleBookPrice;

    private String DbConnectionString;

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
}
