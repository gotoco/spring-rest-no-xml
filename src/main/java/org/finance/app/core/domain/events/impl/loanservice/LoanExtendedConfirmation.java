package org.finance.app.core.domain.events.impl.loanservice;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.ddd.annotation.Event;

import java.io.Serializable;


@Event
public class LoanExtendedConfirmation implements Serializable {

    private final AggregateId aggregateId;

    public LoanExtendedConfirmation(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }
}
