package org.finance.app.core.domain.saga;


import org.finance.app.core.ddd.annotation.LoadSaga;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.customerservice.RequestWasSubmitted;

public interface LoanSagaManager<T extends SagaInstance<D>, D> {

    void removeSaga(T saga);

    D createNewSagaData();

    public void handleRequestWasSubmitted(Object event);

    public void handleExtendTheLoanRequest(Object event);

    public void handleCheckedIpEvent(Object event);

    public void handleRiskAnalyzedEvent(Object event);

    @LoadSaga
    public D loadSaga(RequestWasSubmitted event);

    @LoadSaga
    public D loadSaga(ExtendTheLoanRequest event);

    public D createNewSagaData(AggregateId id );

    public D createAndFillNewSagaData(AggregateId id, RequestWasSubmitted requestEvent);

    public D createAndFillNewSagaData(AggregateId id, ExtendTheLoanRequest requestEvent);
}
