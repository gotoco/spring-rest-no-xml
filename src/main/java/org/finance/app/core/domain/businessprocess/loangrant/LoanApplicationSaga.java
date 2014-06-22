package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.ddd.annotation.SagaAction;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.loanservice.LoanGrantedConfirmation;
import org.finance.app.core.domain.events.loanservice.LoanRejected;
import org.finance.app.core.domain.events.saga.CheckIpRequest;
import org.finance.app.core.domain.events.saga.DoExtendLoanRequest;
import org.finance.app.core.domain.events.saga.DoRiskAnalysisRequest;
import org.finance.app.core.domain.saga.SagaInstance;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

public class LoanApplicationSaga extends SagaInstance<LoanApplicationData> {

    private final LoanApplicationData sagaData;

    private DomainEventPublisher eventPublisher;

    public LoanApplicationSaga(LoanApplicationData data){
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

    @SagaAction
    public void completeCheckIp(){
        completeIfPossible();
    }

    @SagaAction
    public void completeRiskAnalysis(){
        completeIfPossible();
    }

    private void requestForIpCheck(){
        AggregateId id = this.sagaData.getRequestId();
        String addressIp = this.sagaData.getIp();
        DateTime submissionDate = new DateTime(this.sagaData.getDateOfApplication());
        CheckIpRequest checkIpRequestEvent = new CheckIpRequest(id, addressIp, submissionDate);

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

    private void completeIfPossible() {
        if (sagaData.hasRisk() != null && sagaData.hasValidIp() ) {
            markAsCompleted();
            if (!sagaData.hasRisk() && sagaData.hasValidIp()){

                LoanGrantedConfirmation eventLoanGranted ;
                Client client = sagaData.getClient();
                Money value = sagaData.getTotalCost();
                DateTime expirationDate = new DateTime(sagaData.getNewExpirationDate());
                DateTime effectiveDate  = new DateTime(sagaData.getDateOfApplication());
                eventLoanGranted = new LoanGrantedConfirmation(sagaData.getRequestId(), client, value, expirationDate, effectiveDate);
                eventPublisher.publish(eventLoanGranted);

            } else {

                LoanRejected eventLoanRejected = new LoanRejected(sagaData.getRequestId());

                eventPublisher.publish(eventLoanRejected);
            }
        }
    }
}
