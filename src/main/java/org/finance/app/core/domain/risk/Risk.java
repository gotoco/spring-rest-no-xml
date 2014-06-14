package org.finance.app.core.domain.risk;

import org.finance.app.core.ddd.annotation.ValueObject;

import javax.persistence.*;

@ValueObject
@Entity
@Table(name = "Risks")
public class Risk implements Comparable{

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

    @Override
    public int compareTo(Object o) {
        Risk risk = (Risk)o;
        return this.cause.compareToIgnoreCase(risk.getCause());
    }
}
