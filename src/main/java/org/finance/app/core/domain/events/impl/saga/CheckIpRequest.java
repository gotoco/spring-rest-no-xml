package org.finance.app.core.domain.events.impl.saga;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.ddd.annotation.Event;
import org.joda.time.DateTime;

import java.io.Serializable;

@Event
public class CheckIpRequest implements Serializable {

    private final AggregateId sagaDataId;

    private final String addressIp;

    private final DateTime submissionDate;

    public CheckIpRequest(AggregateId sagaDataId, String addressIp, DateTime submissionDate){
        this.sagaDataId = sagaDataId;
        this.addressIp = addressIp;
        this.submissionDate = submissionDate;
    }

    public AggregateId getSagaDataId() {
        return sagaDataId;
    }

    public String getAddressIp() {
        return addressIp;
    }

    public DateTime getSubmissionDate(){
        return this.submissionDate;
    }
}
