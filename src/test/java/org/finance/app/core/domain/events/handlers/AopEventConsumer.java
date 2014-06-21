package org.finance.app.core.domain.events.handlers;

import org.finance.app.core.domain.events.engine.mocks.AbstractEventReceiver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Transactional
@Component("aopEventConsumer")
public class AopEventConsumer extends AbstractEventReceiver implements AopEventApi {

    private Boolean ifBasicEventOccurred = false;

    public void handle(Object event) {
        super.handle(event);

        SpringEventHandlerTest.BasicEvent rightEvent = (SpringEventHandlerTest.BasicEvent)event;
        ifBasicEventOccurred = rightEvent != null;
    }

    @Override
    public Boolean ifBasicEventOccurred(){
        Boolean result = ifBasicEventOccurred;
        ifBasicEventOccurred = false;
        return result;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void transactionalMethod(Object event) {
        Query query = entityManager.createQuery("from Client c ");

        System.out.println(query.getResultList().size());
        handle(event);
    }
}
