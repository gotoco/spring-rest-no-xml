package org.finance.app.core.domain.risk.analysis;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.domain.businessprocess.loangrant.GrantingOfLoanSagaManager;
import org.finance.app.core.domain.businessprocess.loangrant.mocks.IpCheckedResponseHandler;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.common.Money;
import org.finance.app.core.domain.common.PersonalData;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.events.impl.saga.CheckIpRequest;
import org.finance.app.core.domain.events.impl.saga.IpCheckedResponse;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.finance.test.ConfigTest;
import org.finance.test.builders.FormBuilder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class RequestMultipleValidatorTest {

    @PersistenceContext
    EntityManager entityManager;

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
    @Transactional
    public void whenCheckIpEventTriggeredAllowAloneIp(){
        //Given
            //Prepare Event
            //Publish Event
        //When
            //pick Event
        //Then
            //should allow This request via IP
    }

    @Test
    @Transactional
    public void whenCheckIpEventTriggeredRejectMultipleIp() {
        //Given
        AggregateId eventId = AggregateId.generate();
        prepareRequestsWithSameIp();
        Form form = new FormBuilder().withCorrectlyFilledForm().build();
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, eventId);
        sagaManager.handleRequestWasSubmitted(requestWasSubmitted);
        CheckIpRequest checkIpRequest = new CheckIpRequest(eventId, "127.0.0.1", new DateTime());
        try {
            eventPublisher.registerEventHandlerByAttributes (IpCheckedResponse.class,
                                                             IpCheckedResponseHandler.class,
                                                             "IpCheckedResponseHandler",
                                                             "handle",
                                                             new Class[]{Object.class},
                                                             applicationContext);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Assert.fail();
        }

        //When
        eventPublisher.publish(checkIpRequest);

        //Then
        IpCheckedResponseHandler responseHandler = (IpCheckedResponseHandler)applicationContext.getBean("IpCheckedResponseHandler");
     //   Assert.assertFalse(responseHandler.isValidIpAddress());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void prepareRequestsWithSameIp() {
        RequestWasSubmitted requestWasSubmitted;

        for(int i=0; i<5; i++) {
            PersonalData personalData = null;
            Money applyingAmount = new Money(2000);
            InetAddress applyingIpAddress = null;
            try {
                applyingIpAddress = InetAddress.getByName("127.0.0.1");
            } catch(UnknownHostException ex){
                ex.printStackTrace();
                Assert.fail("Cannot produce simple IP object");
            }
            Integer maturityInDays = 30;
            DateTime submissionDate = new DateTime();
            Form form = new Form(personalData, applyingAmount, applyingIpAddress, maturityInDays, submissionDate);
            requestWasSubmitted = new RequestWasSubmitted(form, AggregateId.generate());
            sagaManager.handleRequestWasSubmitted(requestWasSubmitted);
        }
    }


}
