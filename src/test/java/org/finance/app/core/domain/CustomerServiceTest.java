package org.finance.app.core.domain;

/**
 * Created by maciek on 02.06.14.
 */

import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.events.SimpleEventPublisher;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.RequestWasSubmitted;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.finance.app.ddd.system.events.EventHandler;
import org.finance.app.mocks.event.EventReceiveNotifier;
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

    @Autowired
    private ApplicationContext appContext;

    @Test
    public void whenFillFormCorrectlyEventFired(){

        //given
        DomainEventPublisher publisher = new SimpleEventPublisher();
        CustomerService customerService = new CustomerService(publisher);
        Form correctlyFilledForm = new Form();
        RequestWasSubmitted event = new RequestWasSubmitted(correctlyFilledForm);
        //EventHandler fillFormCorrectlyEvent = new SpringEventHandler()

        //when
        customerService.fillOutTheForm(correctlyFilledForm);
        //publisher.registerEventHandler();

        //then
    }

    @Test
    public void whenEmptyFormSubmittedNoEventFired(){
        Assert.assertFalse(new Form()  == null);
    }

}


