package org.finance.app.core.domain.events.impl.customerservice;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Form;
import org.finance.app.ddd.annotation.Event;

import java.io.Serializable;

@Event
public class RequestWasSubmitted implements Serializable {

    private Form request;
    private final AggregateId aggregateId;

    public RequestWasSubmitted(Form form, AggregateId aggregateId){
        this.request = form;
        this.aggregateId = aggregateId;
    }

    public RequestWasSubmitted(Form form) {
        this.request = form;
        this.aggregateId = AggregateId.generate();
    }

    public Form getRequest(){
        return this.request;
    }

    public AggregateId getRequestId(){
        return this.aggregateId;
    }
}
