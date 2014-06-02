package org.finance.app.core.domain;

import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.loan.Loan;
import org.finance.app.ddd.annotation.AggregateRoot;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by maciek on 02.06.14.
 */
@AggregateRoot
public class CustomerService {

    private DomainEventPublisher eventPublisher;

    @Autowired
    public CustomerService (DomainEventPublisher evPublisher){
        this.eventPublisher = evPublisher;
    }


    public void applyForaLoan(){

    }

    public Loan browseLoanHistory(){

        return null;
    }

    public void fillOutTheForm(Form form){

    }

    public void extendTheLoan(Loan loan){

    }
}
