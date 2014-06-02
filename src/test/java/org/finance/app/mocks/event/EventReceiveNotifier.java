package org.finance.app.mocks.event;

/**
 * Created by maciek on 02.06.14.
 */

import org.springframework.stereotype.Component;

@Component("EventReceiveNotifier")
public class EventReceiveNotifier {

    private Boolean eventOccurred;
    private Class<?> latestEventType;

    public void handle(Object event) {
        this.latestEventType = event.getClass();
        this.eventOccurred = true;
    }

    public Boolean isRightEventOccurred(Object eventType){

        Boolean result = this.latestEventType.isAssignableFrom(eventType.getClass());

        return result;
    }
}
