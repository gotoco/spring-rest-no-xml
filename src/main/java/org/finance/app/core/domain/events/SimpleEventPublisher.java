package org.finance.app.core.domain.events;

/**
 * Created by maciek on 02.06.14.
 */

import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.app.core.ddd.system.events.EventHandler;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component("SimpleEventPublisher")
public class SimpleEventPublisher implements DomainEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventPublisher.class);

    private Set<EventHandler> eventHandlers = new HashSet<EventHandler>();

    public void registerEventHandler(EventHandler handler) {
        eventHandlers.add(handler);
    }

    @Override
    public void registerEventHandlerByAttributes(Class<?> eventType, String beanName, Method method, BeanFactory beanFactory) {
        SpringEventHandler eventHandler = new SpringEventHandler(eventType, beanName, method, beanFactory);
        this.registerEventHandler(eventHandler);
    }

    @Override
    public void registerEventHandlerByAttributes(Class<?> eventType, Class<?> BeanClass, String beanName, String methodName, Class[] args, BeanFactory beanFactory) throws NoSuchMethodException {
        Method method = BeanClass.getMethod(methodName, args);
        SpringEventHandler eventHandler = new SpringEventHandler(eventType, beanName, method, beanFactory);
        this.registerEventHandler(eventHandler);
    }

    @Override
    public void publish(Serializable event) {
        doPublish(event);
    }

    protected void doPublish(Object event) {
        for (EventHandler handler : new ArrayList<EventHandler>(eventHandlers)) {
            if (handler.canHandle(event)) {
                try {
                    handler.handle(event);
                } catch (Exception e) {
                    LOGGER.error("event handling error", e);
                }
            }
        }
    }
}
