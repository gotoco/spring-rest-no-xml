package org.finance.app.ddd.system.events;

/**
 * Created by maciek on 02.06.14.
 */
public interface EventHandler {
    boolean canHandle(Object event);

    void handle(Object event);
}
