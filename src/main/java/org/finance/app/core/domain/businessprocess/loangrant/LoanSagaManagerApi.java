package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.ddd.annotation.LoadSaga;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.events.impl.saga.IpCheckedResponse;
import org.finance.app.core.domain.events.impl.saga.RiskAnalyzedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import java.lang.reflect.Method;

/**
 * Created by maciek on 10.06.14.
 */
public interface LoanSagaManagerApi {

    @LoadSaga
    public GrantingOfLoanData loadSaga(RequestWasSubmitted event);

    @LoadSaga
    public GrantingOfLoanData loadSaga(ExtendTheLoanRequest event);

    public void removeSaga(GrantingOfLoanSaga saga);

    public GrantingOfLoanData createNewSagaData();

    public GrantingOfLoanData createNewSagaData(AggregateId id );

    public GrantingOfLoanData createAndFillNewSagaData(AggregateId id, RequestWasSubmitted requestEvent);

    public GrantingOfLoanData createAndFillNewSagaData(AggregateId id, ExtendTheLoanRequest requestEvent);

    public void handleRequestWasSubmitted(Object event);

    public void handleExtendTheLoanRequest(Object event);

    public void handleCheckedIpEvent(Object event);

    public void handleRiskAnalyzedEvent(Object event);


}
