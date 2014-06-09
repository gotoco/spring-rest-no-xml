package org.finance.app.core.domain.events.impl.customerservice;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.loan.Loan;
import org.finance.app.ddd.annotation.Event;

import java.io.Serializable;

@Event
public class ExtendTheLoanRequest implements Serializable {

    private Loan baseLoan;
    private final AggregateId aggregateId;

    public ExtendTheLoanRequest(Loan loan, AggregateId aggregateId) {
        this.baseLoan = loan;
        this.aggregateId = aggregateId;
    }

    public ExtendTheLoanRequest(Loan loan) {
        this.baseLoan = loan;
        this.aggregateId = AggregateId.generate();
    }

    public Long getLoanId(){
        return this.baseLoan.getLoan_id();
    }

    public AggregateId getAggregateId(){
        return this.aggregateId;
    }

    public Loan getBaseLoan() {
        return baseLoan;
    }
}
