package org.finance.app.core.domain.events.customerservice;

import org.finance.app.core.ddd.annotation.Event;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.sharedcore.objects.Form;

import java.io.Serializable;

@Event
public class RequestWasSubmitted implements Serializable {

    private Form request;
    private final AggregateId aggregateId;

    public RequestWasSubmitted(Form form, AggregateId aggregateId){
        this.request = form;
        this.aggregateId = aggregateId;
    }

    public Form getRequest(){
        return this.request;
    }

    public AggregateId getRequestId(){
        return this.aggregateId;
    }
}
