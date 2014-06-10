package org.finance.app.core.domain.saga;


public interface SagaManager<T extends SagaInstance<D>, D> {

    void removeSaga(T saga);

    D createNewSagaData();
}
