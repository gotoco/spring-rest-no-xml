package org.finance.app.core.domain.risk;

import org.finance.app.core.ddd.annotation.ValueObject;

@ValueObject
public class Risk {

    private final Boolean riskExistence;

    private String cause;

    public Risk(Boolean riskExistence){
        this.riskExistence = riskExistence;
    }

    public Risk(Boolean riskExistence, String cause){
        this.riskExistence = riskExistence;
        this.cause = cause;
    }

    public Boolean isRiskExistence() {
        return riskExistence;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
