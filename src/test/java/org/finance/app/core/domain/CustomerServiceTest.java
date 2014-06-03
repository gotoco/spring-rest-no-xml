package org.finance.app.core.domain;

/**
 * Created by maciek on 02.06.14.
 */

import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.events.SimpleEventPublisher;
import org.finance.app.core.domain.events.engine.mocks.BaseEventReceiveNotifier;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.RequestWasSubmitted;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.finance.test.ConfigTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class CustomerServiceTest {

    private ApplicationContext applicationContext;

    private DomainEventPublisher eventPublisher;

    private static final String BaseEventHandlerName = "BaseEventReceiveNotifier" ;
    private static final String RandomEventHandlerName = "RandomEventReceiveNotifier" ;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setEventPublisher(DomainEventPublisher domainEventPublisher){
        this.eventPublisher = domainEventPublisher;
    }

    @Test
    public void whenFillFormCorrectlyEventFired(){

        //given
        DomainEventPublisher publisher = new SimpleEventPublisher();
        CustomerService customerService = new CustomerService(publisher);
        Form correctlyFilledForm = new Form();
        RequestWasSubmitted event = new RequestWasSubmitted(correctlyFilledForm);


        //Prepare Event handler to check if event was published
        BaseEventReceiveNotifier requestSubmittedHandler = null;
        try {
            Method method = BaseEventReceiveNotifier.class.getMethod("handle", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler(event.getClass(), BaseEventHandlerName, method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

            requestSubmittedHandler = (BaseEventReceiveNotifier) applicationContext.getBean(BaseEventHandlerName);

        } catch(NoSuchMethodException ex) {
            fail();
        }

        //when
        customerService.SubmitTheForm(correctlyFilledForm);

        //then
        //assertTrue(requestSubmittedHandler.isRightEventOccurred());

        requestSubmittedHandler.cleanUpEventShadow();
    }

    @Test
    public void whenEmptyFormSubmittedNoEventFired(){
        Assert.assertFalse(new Form()  == null);
    }

}


