package org.finance.app.core.domain;

/**
 * Created by maciek on 02.06.14.
 */

import junit.framework.Assert;
import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.events.SimpleEventPublisher;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.junit.Test;

public class CustomerServiceTest {

    @Test
    public void whenFillFormCorrectlyEventFired(){

        //given
        DomainEventPublisher publisher = new SimpleEventPublisher();
        CustomerService customerService = new CustomerService(publisher);
        Form correctlyFilledForm = new Form();

        //when
        customerService.fillOutTheForm(correctlyFilledForm);

        //then
        Assert.assertFalse(publisher == null);
    }

    @Test
    public void whenEmptyFormSubmittedNoEventFired(){

    }

}
