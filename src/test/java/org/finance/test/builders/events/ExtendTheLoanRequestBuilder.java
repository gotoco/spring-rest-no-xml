package org.finance.test.builders.events;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.customerservice.ExtendTheLoanRequest;
import org.finance.app.sharedcore.objects.Loan;
import org.joda.time.DateTime;

public class ExtendTheLoanRequestBuilder {

    private Loan baseLoan;
    private AggregateId aggregateId;
    private DateTime newExpirationDate;

    public ExtendTheLoanRequestBuilder(){

    }

    public ExtendTheLoanRequestBuilder withLoanFor7Days(Loan loan){
        this.baseLoan = loan;
        this.aggregateId = AggregateId.generate();
        this.newExpirationDate = new DateTime(loan.getExpirationDate()).plusDays(7);
        return this;
    }

    public ExtendTheLoanRequest build(){
        return new ExtendTheLoanRequest(baseLoan, aggregateId, newExpirationDate);
    }
}
