package de.agiledojo.potterstore;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix="potter")
public class PotterStoreConfiguration {

    private double singleBookPrice;

    public double getSingleBookPrice() {
        return singleBookPrice;
    }

    public void setSingleBookPrice(double singleBookPrice) {
        this.singleBookPrice = singleBookPrice;
    }
}
