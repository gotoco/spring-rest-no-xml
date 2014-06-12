package org.finance.app.core.domain.events.loanservice;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.ddd.annotation.Event;

import java.io.Serializable;

/**
 * Created by maciek on 05.06.14.
 */
@Event
public class LoanRejected implements Serializable {

    private final AggregateId aggregateId;

    public LoanRejected(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }
}
