package org.finance.app.core.domain.saga;


import org.finance.app.core.ddd.annotation.LoadSaga;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;

public interface SagaManager<T extends SagaInstance<D>, D> {

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
}
