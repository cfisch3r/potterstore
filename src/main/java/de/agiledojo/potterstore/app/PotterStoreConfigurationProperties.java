package de.agiledojo.potterstore.app;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="potter")
public class PotterStoreConfigurationProperties {

    private Double singleBookPrice;

    private String currencyCode;

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
}
