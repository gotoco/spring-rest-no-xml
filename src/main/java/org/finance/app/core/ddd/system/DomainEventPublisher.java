package org.finance.app.core.ddd.system;

/**
 * Created by maciek on 02.06.14.
 */

import org.finance.app.core.ddd.system.events.EventHandler;
import org.springframework.beans.factory.BeanFactory;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface DomainEventPublisher {
    void publish(Serializable event);

    public void registerEventHandler(EventHandler handler);

    public void registerEventHandlerByAttributes(Class<?> eventType,
                                                 String beanName,
                                                 Method method,
                                                 BeanFactory beanFactory);

    public void registerEventHandlerByAttributes(Class<?> eventType,
                                                 Class<?> BeanClass,
                                                 String beanName,
                                                 String methodName,
                                                 Class[] args,
                                                 BeanFactory beanFactory) throws NoSuchMethodException;

    }
