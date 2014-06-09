package org.finance.app.core.ddd.system.events;

/**
 * Created by maciek on 02.06.14.
 */
public interface EventHandler {
    boolean canHandle(Object event);

    void handle(Object event);
}
