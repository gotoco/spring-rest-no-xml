package org.finance.app.core.ddd.system.events;


public interface EventHandler {
    boolean canHandle(Object event);

    void handle(Object event);
}
