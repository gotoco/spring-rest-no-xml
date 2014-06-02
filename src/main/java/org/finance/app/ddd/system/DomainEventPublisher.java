package org.finance.app.ddd.system;

/**
 * Created by maciek on 02.06.14.
 */

import org.finance.app.ddd.system.events.EventHandler;

import java.io.Serializable;

public interface DomainEventPublisher {
    void publish(Serializable event);

    public void registerEventHandler(EventHandler handler);
}
