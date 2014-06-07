package org.finance.app.core.domain.saga;

/**
 * Created by maciek on 07.06.14.
 */
public interface SagaManager<T extends SagaInstance<D>, D> {

    void removeSaga(T saga);

    D createNewSagaData();
}
