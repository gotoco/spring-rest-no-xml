package org.finance.app.core.domain.businessprocess.loangrant;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.saga.LoanSagaManager;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.core.domain.events.customerservice.RequestWasSubmitted;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.test.ConfigTest;
import org.finance.test.builders.FormBuilder;
import org.finance.test.builders.PersonalDataBuilder;
import org.finance.test.builders.events.ExtendTheLoanRequestBuilder;
import org.finance.test.builders.loan.LoanBuilder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
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
public class LoanApplicationSagaManagerTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DomainEventPublisher eventPublisher;

    @Autowired
    private LoanSagaManager<LoanApplicationSaga, LoanApplicationData> loanSagaManager;

    @Test
    @Transactional
    @Rollback(true)
    public void newRequestSubmittedNewSagaCreated(){

        //Given
        Client client = createAndSaveNewClient();
        Form form = new FormBuilder().withCorrectlyFilledForm(client).build();
        AggregateId aggregateId = AggregateId.generate();
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, aggregateId);

        //When
        eventPublisher.publish(requestWasSubmitted);
        LoanApplicationData saga = loanSagaManager.loadSaga(requestWasSubmitted);

        //Then
        Assert.assertNotNull(saga.getRequestId());
        Assert.assertEquals(saga.getRequestId(), requestWasSubmitted.getRequestId());

    }

    @Test
    @Transactional
    @Rollback(true)
    public void loanExistExtendLoanRequestExistingSagaLoaded(){

        //Given
        Loan loanToExtend = createAndSaveLoan();
        ExtendTheLoanRequest extendTheLoanRequest = new ExtendTheLoanRequestBuilder().withLoanFor7Days(loanToExtend).build();

        //When
        eventPublisher.publish(extendTheLoanRequest);
        LoanApplicationData saga = loanSagaManager.loadSaga(extendTheLoanRequest);

        //Then
        Assert.assertNotNull(saga.getRequestId());
        Assert.assertEquals(saga.getRequestId(), extendTheLoanRequest.getAggregateId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void requestWasSubmittedHandled(){

        //Given
        Client client = createAndSaveNewClient();
        Form form = new FormBuilder().withCorrectlyFilledForm(client).build();
        AggregateId aggregateId = createNewSagaDataAndGetId();
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, aggregateId);

        //When
        eventPublisher.publish(requestWasSubmitted);
        LoanApplicationData loanApplicationData = loanSagaManager.loadSaga(requestWasSubmitted);

        //Then
        Assert.assertNotNull(loanApplicationData);
        Assert.assertEquals(aggregateId, loanApplicationData.getRequestId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void extendTheLoanRequestHandled(){

        //Given
        Client client = createAndSaveNewClient();
        Form form = new FormBuilder().withCorrectlyFilledForm(client).build();
        AggregateId aggregateId = createNewSagaDataAndGetId();
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, aggregateId);

        //When
        eventPublisher.publish(requestWasSubmitted);
        LoanApplicationData loanApplicationData = loanSagaManager.loadSaga(requestWasSubmitted);

        //Then
        Assert.assertNotNull(loanApplicationData);
        Assert.assertEquals(aggregateId, loanApplicationData.getRequestId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void whenRandomSagaLoadedExceptionThrowed(){

        //Given
        AggregateId aggregateId = AggregateId.generate();
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(new FormBuilder().withEmptyForm().build(), aggregateId);

        //When
        LoanApplicationData sagaData =  tryToLoadSagaDataForRequest(requestWasSubmitted);

        //Then
        Assert.assertNull(sagaData);

    }

    private LoanApplicationData tryToLoadSagaDataForRequest(RequestWasSubmitted requestWasSubmitted) {
        LoanApplicationData sagaData = null;
        try {
            sagaData = loanSagaManager.loadSaga(requestWasSubmitted);
            fail("Random saga should not existed");
        }
        catch(NoResultException exception){

        }

        return sagaData;
    }

    @Transactional
    public Client createAndSaveNewClient(){
        Client client = new PersonalDataBuilder().withCorrectlyFilledData().build();
        entityManager.persist(client);
        return client;
    }

    @Transactional
    public Loan createAndSaveLoan(){
        Loan loan = new LoanBuilder().withDefaultData().build();
        entityManager.persist(loan);
        return loan;
    }

    public AggregateId createNewSagaDataAndGetId(){
        AggregateId aggregateId = AggregateId.generate();
        loanSagaManager.createNewSagaData(aggregateId);

        return aggregateId;
    }
}
