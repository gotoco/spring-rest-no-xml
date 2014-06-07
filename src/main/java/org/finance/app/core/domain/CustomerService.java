package org.finance.app.core.domain;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.common.loan.Loan;
import org.finance.app.ddd.annotation.AggregateRoot;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("CustomerService")
@AggregateRoot
public class CustomerService {

    private DomainEventPublisher eventPublisher;

    private Form submittedForm;

    @Autowired
    public CustomerService (DomainEventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    public void applyForaLoan(){

        if( submittedForm != null ){
            Form formToSend = submittedForm;
            cleanUpForm();
            eventPublisher.publish(new RequestWasSubmitted(formToSend, AggregateId.generate()));
        } else {
            throw new IllegalStateException("Before applying for a Loan Form must be submitted"); //TODO: create spifi class
        }
    }

    public Loan browseLoanHistory(){

        return null;
    }

    public Boolean submitTheForm(Form form){
        Boolean isCorrectlyFilled = validateForm(form);

        if(isCorrectlyFilled){
            this.submittedForm = form;
        }

        return isCorrectlyFilled;
    }

    public void extendTheLoan(Loan loan){
        eventPublisher.publish(new ExtendTheLoanRequest(loan, AggregateId.generate()) );
    }

    private Boolean validateForm(Form form) {
        return !form.isFormEmpty(); //TODO: Implement validator
    }

    private void cleanUpForm(){
        this.submittedForm = null;
    }

}
