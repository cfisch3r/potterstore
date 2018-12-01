package de.agiledojo.potterstore.app.repository;

import javax.persistence.*;

@Entity
@Table(name="PARAMETER")
public class ParameterRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String paramKey;

    String paramValue;

    public ParameterRecord() {
        super();
    }

    public ParameterRecord(String paramKey, String paramValue) {
        this.paramKey = paramKey;
        this.paramValue = paramValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String value) {
        this.paramValue = value;
    }


}
