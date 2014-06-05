package org.finance.app.core.domain.events.impl.customerservice;

import org.finance.app.core.domain.common.Form;
import org.finance.app.ddd.annotation.Event;

import java.io.Serializable;

/**
 * Created by maciek on 02.06.14.
 */
@Event
public class RequestWasSubmitted implements Serializable {

    private Form request;

    public RequestWasSubmitted(Form form){
        request = form;
    }

    public Form getRequest(){
        return this.request;
    }

}
