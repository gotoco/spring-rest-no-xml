package org.finance.app.core.domain;

import org.finance.app.core.ddd.annotation.DomainService;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.customerservice.RequestWasSubmitted;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Loan;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("CustomerService")
@DomainService
public class CustomerService {

    private DomainEventPublisher eventPublisher;

    @Autowired
    public CustomerService (DomainEventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    public void applyForaLoan(Form form){

        Boolean isCorrectlyFilled = validateForm(form);

        if(isCorrectlyFilled){
            eventPublisher.publish(new RequestWasSubmitted(form, AggregateId.generate()));
        } else {
            throw new IllegalStateException("Before applying for a Loan correct Form must be submitted");
        }
    }

    public void extendTheLoan(Loan loan, DateTime newExpirationDate){
        eventPublisher.publish(new ExtendTheLoanRequest(loan, AggregateId.generate(), newExpirationDate) );
    }

    private Boolean validateForm(Form form) {
        return !form.isFormEmpty();
    }

}
