package org.finance.app.core.domain.risk.analysis;


import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.saga.CheckIpRequest;
import org.finance.app.core.domain.events.impl.saga.DoRiskAnalysisRequest;
import org.finance.app.core.domain.risk.RiskAnalysisFunction;
import org.finance.app.ddd.annotation.DomainService;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

@DomainService
@Component("RequestMultipleValidator")
public class RequestMultipleValidator {

    private final DomainEventPublisher eventPublisher;

    private ApplicationContext applicationContext;

    @Autowired
    public RequestMultipleValidator(DomainEventPublisher eventPublisher, ApplicationContext applicationContext) {
        this.eventPublisher = eventPublisher;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void registerEventHandler() {
        try {
            Method method = RiskAnalysisService.class.getMethod("analyzeRisk", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler(CheckIpRequest.class, "RiskAnalysisService", method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

}
