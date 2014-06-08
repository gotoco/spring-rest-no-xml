package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.annotations.IntegrationTest;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.finance.test.ConfigTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by maciek on 08.06.14.
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class GrantingOfLoanSagaTest {

    private ApplicationContext applicationContext;

    private DomainEventPublisher eventPublisher;

    private GrantingOfLoanSagaManager sagaManager;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setEventPublisher(DomainEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setSagaManager(GrantingOfLoanSagaManager sagaManager) {
        this.sagaManager = sagaManager;
    }

    @Test
    public void onCompleteEventsTriggered(){
        //Given
            //Create new data
            //Load new saga
            //Create fake event handlers

        //When
            //saga.OnComplete

        //Then
            //Events handlers
    }

}
