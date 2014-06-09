package org.finance.app.core.domain;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.common.loan.Loan;
import org.finance.app.core.ddd.annotation.DomainService;
import org.finance.app.core.ddd.system.DomainEventPublisher;
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

    public Loan browseLoanHistory(){

        return null;
    }

    public void extendTheLoan(Loan loan){
        eventPublisher.publish(new ExtendTheLoanRequest(loan, AggregateId.generate()) );
    }

    private Boolean validateForm(Form form) {
        return !form.isFormEmpty();
    }

}
