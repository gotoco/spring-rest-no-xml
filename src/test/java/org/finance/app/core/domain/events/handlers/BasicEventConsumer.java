package org.finance.app.core.domain.events.handlers;

import org.finance.app.core.domain.events.engine.mocks.AbstractEventReceiver;
import org.springframework.stereotype.Component;

@Component("basicEventConsumer")
public class BasicEventConsumer extends AbstractEventReceiver {

    private Boolean ifBasicEventOccurred = false;

    public void handle(Object event) {
        super.handle(event);

        SpringEventHandlerTest.BasicEvent rightEvent = (SpringEventHandlerTest.BasicEvent)event;
        ifBasicEventOccurred = rightEvent != null;
    }

    public Boolean ifBasicEventOccurred(){
        Boolean result = ifBasicEventOccurred;
        ifBasicEventOccurred = false;
        return result;
    }
}
