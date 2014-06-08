package org.finance.app.core.domain.events.impl.saga;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Money;
import org.finance.app.ddd.annotation.Event;
import org.joda.time.DateTime;

import java.io.Serializable;

@Event
public class DoRiskAnalysisRequest implements Serializable {

    private final AggregateId sagaDataId;

    private final DateTime applicationTime;

    private final Money loanValue;

    private final DateTime expirationDate;

    public DoRiskAnalysisRequest(AggregateId sagaDataId, DateTime applicationTime, Money value, DateTime date){
        this.sagaDataId = sagaDataId;
        this.applicationTime = applicationTime;
        this.loanValue = value;
        this.expirationDate = date;
    }

    public AggregateId getSagaDataId() {
        return sagaDataId;
    }

    public DateTime getApplicationTime() {
        return applicationTime;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public Money getLoanValue() {
        return loanValue;
    }
}
