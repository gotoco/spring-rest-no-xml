package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Money;
import org.finance.app.core.domain.common.loan.Loan;
import org.finance.app.core.domain.events.impl.saga.CheckIpRequest;
import org.finance.app.core.domain.events.impl.saga.DoExtendLoanRequest;
import org.finance.app.core.domain.events.impl.saga.DoRiskAnalysisRequest;
import org.finance.app.core.domain.saga.SagaInstance;
import org.finance.app.ddd.annotation.SagaAction;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by maciek on 05.06.14.
 */
public class GrantingOfLoanSaga  extends SagaInstance<GrantingOfLoanData> {

    private final GrantingOfLoanData sagaData;

    private DomainEventPublisher eventPublisher;

    public GrantingOfLoanSaga(GrantingOfLoanData data){
        this.sagaData = data;
    }

    @Autowired
    public void setEventPublisher(DomainEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @SagaAction
    public void completeLoanRequest() {

        requestForIpCheck();
        requestForRiskAnalysis();
    }

    @SagaAction
    public void completeExtendsLoan(){

        requestForLoanExtended();
    }

    private void requestForIpCheck(){
        AggregateId id = this.sagaData.getRequestId();
        String addressIp = this.sagaData.getIp();
        CheckIpRequest checkIpRequestEvent = new CheckIpRequest(id, addressIp);

        eventPublisher.publish(checkIpRequestEvent);
    }

    private void requestForRiskAnalysis(){
        DateTime dateOfApplication = new DateTime(this.sagaData.getDateOfApplication());
        AggregateId id = this.sagaData.getRequestId();
        Money loanValue = this.sagaData.getTotalCost();
        DateTime expirationDate = new DateTime(this.sagaData.getNewExpirationDate());
        DoRiskAnalysisRequest checkRiskAnalysisEvent = new DoRiskAnalysisRequest(id, dateOfApplication, loanValue, expirationDate);

        eventPublisher.publish(checkRiskAnalysisEvent);
    }

    private void requestForLoanExtended(){
        AggregateId id = this.sagaData.getRequestId();
        Money loanValue = this.sagaData.getTotalCost();
        DateTime expirationDate = new DateTime(this.sagaData.getNewExpirationDate());
        Long sagaFixedId = this.sagaData.getFixedId();
        Loan loan = sagaData.getLoan();
        DoExtendLoanRequest extendLoanRequest = new DoExtendLoanRequest(sagaFixedId, loan, expirationDate, id, loanValue);

        eventPublisher.publish(extendLoanRequest);
    }
}
