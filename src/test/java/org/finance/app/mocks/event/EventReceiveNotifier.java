package org.finance.app.mocks.event;

/**
 * Created by maciek on 02.06.14.
 */

import org.springframework.stereotype.Component;

@Component("EventReceiveNotifier")
public class EventReceiveNotifier {

    private Boolean eventOccurred;

    public void handle(Object event) {
        this.eventOccurred = true;
    }

    public Boolean isRightEventOccurred(){
        return this.eventOccurred;
    }
}
