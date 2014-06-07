package org.finance.app.core.domain.events.impl.loanservice;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.ddd.annotation.Event;

import java.io.Serializable;

/**
 * Created by maciek on 05.06.14.
 */
@Event
public class LoanExtended implements Serializable {

    private final AggregateId aggregateId;

    public LoanExtended(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }
}
