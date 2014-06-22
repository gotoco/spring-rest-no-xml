package org.finance.app.core.domain.risk.analysis;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.application.parent.ApplicationEnvSpecifiedFunctionalities;
import org.finance.app.core.domain.businessprocess.loangrant.LoanApplicationData;
import org.finance.app.core.domain.businessprocess.loangrant.mocks.IpCheckedResponseHandler;
import org.finance.app.core.domain.common.*;
import org.finance.app.core.domain.events.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.events.saga.CheckIpRequest;
import org.finance.app.core.domain.events.saga.IpCheckedResponse;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.app.core.domain.saga.LoanSagaManager;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Money;
import org.finance.test.ConfigTest;
import org.finance.test.builders.FormBuilder;
import org.finance.test.builders.PersonalDataBuilder;
import org.joda.time.DateTime;
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
import javax.persistence.PersistenceContext;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class RequestMultipleValidatorTest extends ApplicationEnvSpecifiedFunctionalities {

    private static final String ipCheckedResponseHandlerName = "ipCheckedResponseHandler";

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    EntityManager entityManager;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DomainEventPublisher eventPublisher;

    @Autowired
    private LoanSagaManager loanApplicationSagaManager;

    @Test
    @Transactional
    @Rollback(true)
    public void whenDbIsEmptyFirstEventShouldBeUpdated(){

        //Given
        AggregateId eventId = AggregateId.generate();
        registerCheckedIpEventHandler();

        //When
        submitSingleRequest(eventId);

        //Then
        IpCheckedResponseHandler responseHandler = (IpCheckedResponseHandler)applicationContext.getBean(ipCheckedResponseHandlerName);
        Assert.assertNotNull(responseHandler.isValidIpAddress());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void whenCheckIpEventTriggeredAllowAloneIp(){

        //Given
        AggregateId eventId = AggregateId.generate();
        submitSingleRequest(eventId);
        CheckIpRequest checkIpRequest = new CheckIpRequest(eventId, "127.0.0.1", new DateTime());
        registerCheckedIpEventHandler();

        //When
        eventPublisher.publish(checkIpRequest);

        //Then
        IpCheckedResponseHandler responseHandler = (IpCheckedResponseHandler)applicationContext.getBean(ipCheckedResponseHandlerName);
        Assert.assertTrue(responseHandler.isValidIpAddress());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void whenCheckIpEventTriggeredRejectMultipleIp() {

        //Given
        AggregateId eventId = AggregateId.generate();
        when5SubmitRequest(eventId);
        CheckIpRequest checkIpRequest = new CheckIpRequest(eventId, "127.0.0.1", new DateTime());
        registerCheckedIpEventHandler();

        //When
        eventPublisher.publish(checkIpRequest);

        //Then
        IpCheckedResponseHandler responseHandler = (IpCheckedResponseHandler)applicationContext.getBean(ipCheckedResponseHandlerName);
        Assert.assertFalse(responseHandler.isValidIpAddress());
    }

    public void registerCheckedIpEventHandler(){

        try {
            eventPublisher.registerEventHandlerByAttributes (IpCheckedResponse.class,
                                                             IpCheckedResponseHandler.class,
                                                             ipCheckedResponseHandlerName,
                                                             "handle",
                                                             new Class[]{Object.class},
                                                             applicationContext);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Transactional
    private void prepareRequestsWithSameIp() {
        RequestWasSubmitted requestWasSubmitted;

        for(int i=0; i<5; i++) {
            Client personalData = new PersonalDataBuilder().withCorrectlyFilledData().withFirstName("Jan"+i).build();
            entityManager.persist(personalData);
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
            LoanApplicationData createdNewSagaData = (LoanApplicationData)loanApplicationSagaManager.createAndFillNewSagaData(AggregateId.generate(), requestWasSubmitted);
            createdNewSagaData.whenIpValidIsChecked(new IpCheckedResponse(AggregateId.generate() ,false) );
            entityManager.persist(createdNewSagaData);
        }
    }

    private void when5SubmitRequest(AggregateId eventId){
        prepareRequestsWithSameIp();
        submitSingleRequest(eventId);
    }

    private void submitSingleRequest(AggregateId eventId){
        Client client = createAndSaveClientRecordToDb();
        Form form = new FormBuilder().withCorrectlyFilledForm(client).build();
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, eventId);
        loanApplicationSagaManager.handleRequestWasSubmitted(requestWasSubmitted);
    }

}
