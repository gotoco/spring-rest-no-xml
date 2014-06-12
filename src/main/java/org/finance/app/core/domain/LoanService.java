package org.finance.app.core.domain;

import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.app.core.domain.common.loan.ExtendTheLoanFunction;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.loanservice.LoanGrantedConfirmation;
import org.finance.app.core.domain.events.impl.saga.IpCheckedResponse;
import org.finance.app.core.domain.saga.SagaManager;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.core.domain.common.Decision;
import org.finance.app.core.domain.risk.Risk;
import org.finance.app.core.ddd.annotation.AggregateRoot;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Method;

@AggregateRoot
@Component("loanService")
public class LoanService {

    private static final String loanServiceName = "loanService";

    private ApplicationContext applicationContext;

    private DomainEventPublisher eventPublisher;

    private ExtendTheLoanFunction extendTheLoanFunction;

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void registerForHandlingEvents(){
        registerHandleGrantLoan();
        registerHandleExtendLoan();
    }

    @Autowired
    public LoanService(ApplicationContext applicationContext,
                       DomainEventPublisher eventPublisher,
                       ExtendTheLoanFunction extendTheLoanFunction) {
        this.applicationContext = applicationContext;
        this.eventPublisher = eventPublisher;
        this.extendTheLoanFunction = extendTheLoanFunction;
    }

    private void registerHandleGrantLoan() {
        try {
            Method method = LoanService.class.getMethod("decideToGrantLoan", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (LoanGrantedConfirmation.class, loanServiceName, method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private void registerHandleExtendLoan() {
        try {
            Method method = LoanService.class.getMethod("handleExtendALoanRequest", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (ExtendTheLoanRequest.class, loanServiceName, method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    @Transactional
    public void decideToGrantLoan(Object event){

        LoanGrantedConfirmation loanConfirmation = (LoanGrantedConfirmation)event;

        Loan grantedLoan = new Loan(null,
                loanConfirmation.getValue(),
                calculateInterestForNewBusiness(),
                loanConfirmation.getExpirationDate(),
                loanConfirmation.getEffectiveDate(),
                loanConfirmation.getClient());

        entityManager.persist(grantedLoan);
    }

    @Transactional
    public void handleExtendALoanRequest(Object event){
        ExtendTheLoanRequest request = (ExtendTheLoanRequest)event;
        Loan oldLoan = request.getBaseLoan();
        Loan newLoan = extendTheLoanFunction.extend(oldLoan, request.getNewExpirationDate());

        entityManager.persist(newLoan);
    }

    private Money calculateInterestForNewBusiness(){
        return new Money(0);
    }

}
