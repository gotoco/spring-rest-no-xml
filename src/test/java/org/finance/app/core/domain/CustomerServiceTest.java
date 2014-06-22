package org.finance.app.core.domain;

import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.domain.common.*;
import org.finance.app.sharedcore.objects.*;
import org.finance.app.core.domain.events.engine.mocks.BaseEventReceiveNotifier;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.customerservice.RequestWasSubmitted;
import org.finance.app.core.ddd.system.DomainEventPublisher;

import org.finance.test.builders.FormBuilder;
import org.finance.test.builders.PersonalDataBuilder;
import org.finance.test.ConfigTest;
import org.finance.test.builders.contracts.LoanContractBuilder;
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
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class CustomerServiceTest {

    private EntityManager entityManager;

    private ApplicationContext applicationContext;

    private DomainEventPublisher eventPublisher;

    private CustomerService customerService;

    private static final String BaseEventHandlerName = "BaseEventReceiveNotifier" ;
    private static final String RandomEventHandlerName = "RandomEventReceiveNotifier" ;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService){
        this.customerService = customerService;
    }

    @Autowired
    public void setEventPublisher(DomainEventPublisher domainEventPublisher){
        this.eventPublisher = domainEventPublisher;
    }

    @Test
    @Transactional
    @Rollback(true)
    public void whenFillFormCorrectlyEventFired(){

        //given
        Form correctlyFilledForm = fillTheFormAndSave();
        RequestWasSubmitted event =  new RequestWasSubmitted(correctlyFilledForm, AggregateId.generate());
        BaseEventReceiveNotifier requestSubmittedHandler = registerAndGetSubmittedRequestNotifier(event);

        //when
        applyForLoan(correctlyFilledForm);

        //then
        assertTrue(requestSubmittedHandler.isRightEventOccurred());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void whenEmptyFormSubmittedNoEventFired(){

        //given
        Form empty = new FormBuilder().withEmptyForm().build();
        RequestWasSubmitted event = new RequestWasSubmitted(empty, AggregateId.generate());
        BaseEventReceiveNotifier requestSubmittedHandler = registerAndGetSubmittedRequestNotifier(event);

        //when
        tryToApplyForLoan(empty);

        //then
        assertFalse(requestSubmittedHandler.isRightEventOccurred());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void whenLoanExtendRequestSubmitEventFired(){

        //given
        Loan baseLoan = prepareBaseLoanWithDataToExtend();
        ExtendTheLoanRequest event = new ExtendTheLoanRequest(baseLoan, AggregateId.generate(), new DateTime().plusMonths(3));
        BaseEventReceiveNotifier extendedLoamRequestReceiveNotifier = registerAndGetSubmittedRequestNotifier(event);

        //when
        requestForExtendTheLoanForOneMonth(baseLoan);

        //then
        assertTrue(extendedLoamRequestReceiveNotifier.isRightEventOccurred());
    }

    private void tryToApplyForLoan(Form form){
        try {
            customerService.applyForaLoan(form);
            fail("Empty form can't apply for loan");
        } catch (IllegalStateException ise){

        }
    }

    @Transactional
    private Loan prepareBaseLoanWithDataToExtend(){
        LoanContract baseContract = new LoanContractBuilder().withDefaultData().build();
        Loan baseLoan = baseContract.getLatestGrantedLoan();
        entityManager.persist(baseContract);

        return baseLoan;
    }

    @Transactional
    private Form fillTheFormAndSave() {
        Client personalData = new PersonalDataBuilder().withCorrectlyFilledData().build();
        Form filledForm = new FormBuilder().withCorrectlyFilledForm(personalData).build();
        entityManager.persist(personalData);
        return filledForm;
    }

    private BaseEventReceiveNotifier registerAndGetSubmittedRequestNotifier(Serializable event){
        return registerAndGetEventNotifier(event);
    }

    private BaseEventReceiveNotifier registerAndGetLoanExtendedNotifier(Serializable event){
        return registerAndGetEventNotifier(event);
    }

    private BaseEventReceiveNotifier registerAndGetEventNotifier(Serializable event){

        BaseEventReceiveNotifier eventNotifier = null;
        try {
            Method method = BaseEventReceiveNotifier.class.getMethod("handle", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler(event.getClass(), BaseEventHandlerName, method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

            eventNotifier = (BaseEventReceiveNotifier) applicationContext.getBean(BaseEventHandlerName);

        } catch(NoSuchMethodException ex) {
            fail(ex.getMessage());
        }

        return eventNotifier;
    }

    private void applyForLoan(Form submittedForm){
        customerService.applyForaLoan(submittedForm);
    }

    private void requestForExtendTheLoanForOneMonth(Loan loan){
        customerService.extendTheLoan(loan, new DateTime().plusMonths(1));
    }

    @Transactional
    private Loan prepareBasicLoan(){
        Client client = new PersonalDataBuilder().withCorrectlyFilledData().build();
        Money value = new Money(new BigDecimal(3000));
        Money interest = new Money(new BigDecimal(0));
        DateTime expirationDate  = new DateTime().plusDays(30);
        DateTime effectiveDate  = new DateTime();

        Loan loan = new Loan(null, value, interest, expirationDate, effectiveDate, client);
        entityManager.persist(loan);
        return loan;
    }

}


