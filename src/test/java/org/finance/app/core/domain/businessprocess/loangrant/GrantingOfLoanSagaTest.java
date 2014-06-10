package org.finance.app.core.domain.businessprocess.loangrant;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.domain.businessprocess.loangrant.mocks.CheckIpRequestHandler;
import org.finance.app.core.domain.businessprocess.loangrant.mocks.DoRiskAnalysisRequestHandler;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.saga.SagaManager;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.events.impl.saga.CheckIpRequest;
import org.finance.app.core.domain.events.impl.saga.DoRiskAnalysisRequest;
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
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.lang.reflect.Method;

import static org.junit.Assert.fail;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class GrantingOfLoanSagaTest {

    private EntityManager entityManager;

    private ApplicationContext applicationContext;

    private DomainEventPublisher eventPublisher;

    private SagaManager<GrantingOfLoanSaga, GrantingOfLoanData> sagaManager;

    private final static String checkIpRequestHandlerName = "checkIpRequestHandler";
    private final static String doRiskAnalysisRequestHandlerName = "doRiskAnalysisRequestHandler";

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setEventPublisher(DomainEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setSagaManager(SagaManager<GrantingOfLoanSaga, GrantingOfLoanData> sagaManager) {
        this.sagaManager = sagaManager;
    }

    @Test
    @Transactional
    public void onCompleteEventsTriggered(){

        //Given
        Client client = new PersonalDataBuilder().withCorrectlyFilledData().build();
        entityManager.persist(client);
        Form form = new FormBuilder().withCorrectlyFilledForm(client).build();
        AggregateId aggregateId = AggregateId.generate();
        RequestWasSubmitted requestWasSubmitted = new RequestWasSubmitted(form, aggregateId);
//TODO: Fix to assembler method!
        CheckIpRequestHandler checkIpEventNotifier = null;
        DoRiskAnalysisRequestHandler riskAnalysisRequestNotifier = null;
        try {
            Method handleCheckIpMethod      = CheckIpRequestHandler.class.getMethod("handle", new Class[]{Object.class});
            Method handleRiskAnalysisMethod = DoRiskAnalysisRequestHandler.class.getMethod("handle", new Class[]{Object.class});

            SpringEventHandler checkIpEventHandler = new SpringEventHandler
                    (CheckIpRequest.class, checkIpRequestHandlerName, handleCheckIpMethod, applicationContext);
            SpringEventHandler riskAnalysisEventHandler = new SpringEventHandler
                    (DoRiskAnalysisRequest.class, doRiskAnalysisRequestHandlerName, handleRiskAnalysisMethod, applicationContext);

            eventPublisher.registerEventHandler(checkIpEventHandler);
            eventPublisher.registerEventHandler(riskAnalysisEventHandler);

            checkIpEventNotifier = (CheckIpRequestHandler) applicationContext.getBean(checkIpRequestHandlerName);
            riskAnalysisRequestNotifier = (DoRiskAnalysisRequestHandler) applicationContext.getBean(doRiskAnalysisRequestHandlerName);

        } catch(NoSuchMethodException ex) {
            fail(ex.getMessage());
        }

        //When
        eventPublisher.publish(requestWasSubmitted);

        //Then
        Assert.assertTrue(checkIpEventNotifier.isRightEventOccurred());
        Assert.assertTrue(riskAnalysisRequestNotifier.isRightEventOccurred());
    }

}
