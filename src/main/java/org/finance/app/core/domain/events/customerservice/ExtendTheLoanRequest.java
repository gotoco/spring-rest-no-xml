package org.finance.app.core.domain.events.customerservice;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.core.ddd.annotation.Event;
import org.joda.time.DateTime;

import java.io.Serializable;

@Event
public class ExtendTheLoanRequest implements Serializable {

    private Loan baseLoan;
    private final AggregateId aggregateId;
    private DateTime newExpirationDate;

    public ExtendTheLoanRequest(Loan loan, AggregateId aggregateId, DateTime expirationDate) {
        this.baseLoan = loan;
        this.aggregateId = aggregateId;
        this.newExpirationDate = expirationDate;
    }

    public ExtendTheLoanRequest(Loan loan) {
        this.baseLoan = loan;
        this.aggregateId = AggregateId.generate();
    }

    public Long getLoanId(){
        return this.baseLoan.getLoanId();
    }

    public AggregateId getAggregateId(){
        return this.aggregateId;
    }

    public Loan getBaseLoan() {
        return baseLoan;
    }

    public DateTime getNewExpirationDate() {
        return newExpirationDate;
    }
}
