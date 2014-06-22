package org.finance.app.core.domain.events.handlers;

public interface AopEventApi {

    public void transactionalMethod(Object event);

    public Boolean ifBasicEventOccurred();
}
