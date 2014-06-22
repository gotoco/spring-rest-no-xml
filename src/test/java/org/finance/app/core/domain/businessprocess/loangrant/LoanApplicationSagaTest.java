package org.finance.app.core.domain.businessprocess.loangrant;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.application.parent.ApplicationEnvSpecifiedFunctionalities;
import org.finance.app.core.domain.businessprocess.loangrant.mocks.CheckIpRequestHandler;
import org.finance.app.core.domain.businessprocess.loangrant.mocks.DoRiskAnalysisRequestHandler;
import org.finance.app.core.domain.saga.LoanSagaManager;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.events.saga.CheckIpRequest;
import org.finance.app.core.domain.events.saga.DoRiskAnalysisRequest;
import org.finance.app.core.ddd.system.DomainEventPublisher;

import org.finance.app.spring.PersistenceJPAConfig;
import org.finance.test.ConfigTest;
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
import java.lang.reflect.Method;

import static org.junit.Assert.fail;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class LoanApplicationSagaTest extends ApplicationEnvSpecifiedFunctionalities {

    private final static String checkIpRequestHandlerName = "checkIpRequestHandler";
    private final static String doRiskAnalysisRequestHandlerName = "doRiskAnalysisRequestHandler";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DomainEventPublisher eventPublisher;

    @Autowired
    private LoanSagaManager<LoanApplicationSaga, LoanApplicationData> loanSagaManager;

    @Test
    @Transactional
    public void onCompleteEventsTriggered(){

        //Given
        RequestWasSubmitted requestWasSubmitted = prepareRequestWasSubmittedEvent();

        CheckIpRequestHandler checkIpEventNotifier = (CheckIpRequestHandler) createRegisterAndGetEventNotifier
                (CheckIpRequestHandler.class, checkIpRequestHandlerName, CheckIpRequest.class);
        DoRiskAnalysisRequestHandler riskAnalysisRequestNotifier = (DoRiskAnalysisRequestHandler) createRegisterAndGetEventNotifier
                (DoRiskAnalysisRequestHandler.class, doRiskAnalysisRequestHandlerName, DoRiskAnalysisRequest.class);
        //When
        eventPublisher.publish(requestWasSubmitted);

        //Then
        Assert.assertTrue(checkIpEventNotifier.isRightEventOccurred());
        Assert.assertTrue(riskAnalysisRequestNotifier.isRightEventOccurred());
    }

    private Object createRegisterAndGetEventNotifier(Class<?> notifierClass, String notifierBeanName, Class<?> eventClass){

        String notifiersBaseHandlingMethodName = "handle";

        try {
            Method notifierMethod = notifierClass.getMethod(notifiersBaseHandlingMethodName, new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (eventClass, notifierBeanName, notifierMethod, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

            return applicationContext.getBean(notifierBeanName);

        } catch(NoSuchMethodException ex) {
            fail(ex.getMessage());
        }
        return null;
    }

}
