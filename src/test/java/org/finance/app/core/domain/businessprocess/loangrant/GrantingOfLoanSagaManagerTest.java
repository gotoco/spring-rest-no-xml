package org.finance.app.core.domain.businessprocess.loangrant;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.saga.SagaManager;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.test.ConfigTest;
import org.finance.test.builders.FormBuilder;
import org.finance.test.builders.PersonalDataBuilder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.fail;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class GrantingOfLoanSagaManagerTest {

    @PersistenceContext
    EntityManager entityManager;

    private ApplicationContext applicationContext;

    private DomainEventPublisher eventPublisher;

    private SagaManager<GrantingOfLoanSaga, GrantingOfLoanData> sagaManager;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setEventPublisher(DomainEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setSagaManager(SagaManager sagaManager) {
        this.sagaManager = sagaManager;
    }

    @Test
    @Transactional
    public void newRequestSubmittedNewSagaCreated(){

        //Given
        Client client = new PersonalDataBuilder().withCorrectlyFilledData().build();
        entityManager.persist(client);
        Form form = new FormBuilder().withCorrectlyFilledForm(client).build();
        AggregateId aggregateId = AggregateId.generate();
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, aggregateId);

        //When
        eventPublisher.publish(requestWasSubmitted);
        GrantingOfLoanData saga = sagaManager.loadSaga(requestWasSubmitted);

        //Then
        Assert.assertNotNull(saga.getRequestId());
        Assert.assertEquals(saga.getRequestId(), requestWasSubmitted.getRequestId());

    }

    @Test
    @Transactional
    public void loanExistExtendLoanRequestExistingSagaLoaded(){
        //TODO: finish
        //Given
            //Create and save loan
            //create request - extendLoanRequest

        //When
            //Handle request

        //Then
            //Existing saga loaded
    }

    @Test
    @Transactional
    public void requestWasSubmittedHandled(){

        //Given
        Client client = new PersonalDataBuilder().withCorrectlyFilledData().build();
        entityManager.persist(client);
        Form form = new FormBuilder().withCorrectlyFilledForm(client).build();
        AggregateId aggregateId = AggregateId.generate();
        sagaManager.createNewSagaData(aggregateId);
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, aggregateId);

        //When
        eventPublisher.publish(requestWasSubmitted);
        GrantingOfLoanData grantingOfLoanData = sagaManager.loadSaga(requestWasSubmitted);

        //Then
        Assert.assertNotNull(grantingOfLoanData);
        Assert.assertEquals(aggregateId, grantingOfLoanData.getRequestId());
    }

    @Test
    @Transactional
    public void extendTheLoanRequestHandled(){

        //Given
        Client client = new PersonalDataBuilder().withCorrectlyFilledData().build();
        entityManager.persist(client);
        Form form = new FormBuilder().withCorrectlyFilledForm(client).build();
        AggregateId aggregateId = AggregateId.generate();
        sagaManager.createNewSagaData(aggregateId);
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, aggregateId);

        //When
        eventPublisher.publish(requestWasSubmitted);
        GrantingOfLoanData grantingOfLoanData = sagaManager.loadSaga(requestWasSubmitted);

        //Then
        Assert.assertNotNull(grantingOfLoanData);
        Assert.assertEquals(aggregateId, grantingOfLoanData.getRequestId());
    }

    @Test
    public void whenRandomSagaLoadedExceptionThrowed(){

        //Given
        AggregateId aggregateId = AggregateId.generate();
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(new FormBuilder().withEmptyForm().build(), aggregateId);
        GrantingOfLoanData sagaData = null;

        //When
        try {
            sagaData = sagaManager.loadSaga(requestWasSubmitted);
            fail("random saga should not existed");
        }

        //Then
        catch(NoResultException exception){
            Assert.assertNull(sagaData);
        }
    }

    @Test
    public void shouldHandleRiskAnalyzedEvent(){

    }

    @Test
    public void shouldHandleIpCheckedResponseEvent(){

    }
}
