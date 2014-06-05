package org.finance.app.core.domain.saga;

import org.finance.app.ddd.annotation.Saga;

/**
 * Created by maciek on 05.06.14.
 */
@Saga
public class SagaInstance<D> {
    protected D data;
    private boolean completed;

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    protected void markAsCompleted() {
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
