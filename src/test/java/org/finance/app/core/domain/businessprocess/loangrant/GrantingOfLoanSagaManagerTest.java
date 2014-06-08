package org.finance.app.core.domain.businessprocess.loangrant;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.common.loan.Loan;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.saga.SagaManager;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.finance.test.ConfigTest;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
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
    public void newRequestSubmittedNewSagaCreated(){

        //Given
        Form form = createEmptyForm();
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
        Form form = createEmptyForm();
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
        Form form = createEmptyForm();
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
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(createEmptyForm(), aggregateId);
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void prepareEventInDb(AggregateId id){
        Form form = new Form(null, null, null, null, null);
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, id);
    }

    private Form createEmptyForm(){
        return new Form(null, null, null, null, null); //TODO: create assembler
    }
}
