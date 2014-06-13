package org.finance.app.core.domain.risk;

import org.finance.app.core.ddd.annotation.ValueObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ValueObject
@Entity
public class Risk {

    @Id
    @GeneratedValue
    @Column(name="risk_id")
    private Long riskId;

    private final String cause;

    public Risk(String cause){
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public Long getRiskId() {
        return riskId;
    }
}
