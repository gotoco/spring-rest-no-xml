package org.finance.app.core.domain.businessprocess.loangrant;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.domain.businessprocess.loangrant.mocks.CheckIpRequestHandler;
import org.finance.app.core.domain.businessprocess.loangrant.mocks.DoRiskAnalysisRequestHandler;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.events.engine.mocks.BaseEventReceiveNotifier;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.events.impl.saga.CheckIpRequest;
import org.finance.app.core.domain.events.impl.saga.DoRiskAnalysisRequest;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.finance.test.ConfigTest;
import org.finance.test.builders.FormBuilder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

import static org.junit.Assert.fail;

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

    private final static String checkIpRequestHandlerName = "CheckIpRequestHandler";
    private final static String doRiskAnalysisRequestHandlerName = "DoRiskAnalysisRequestHandler";

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
    public void onCompleteEventsTriggered(){

        //Given
        Form form = new FormBuilder().withCorrectlyFilledForm().build();
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


    private Form createEmptyForm(){
        return new Form(null, null, null, null, null); //TODO: create assembler
    }
}
