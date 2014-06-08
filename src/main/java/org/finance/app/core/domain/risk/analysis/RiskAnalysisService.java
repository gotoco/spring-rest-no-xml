package org.finance.app.core.domain.risk.analysis;

import org.finance.app.core.domain.events.impl.saga.DoRiskAnalysisRequest;
import org.finance.app.core.domain.risk.Risk;
import org.finance.app.core.domain.risk.RiskAnalysisFunction;
import org.finance.app.ddd.annotation.DomainService;
import org.finance.app.ddd.system.DomainEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@DomainService
@Component("RiskAnalysisService")
public class RiskAnalysisService {

    private final DomainEventPublisher eventPublisher;

    private ApplicationContext applicationContext;

    RiskAnalysisFunction riskAnalysisFunction;

    @Autowired
    public RiskAnalysisService(DomainEventPublisher eventPublisher, ApplicationContext applicationContext) {
        this.eventPublisher = eventPublisher;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void registerEventHandler() {

    }

    public Risk analyzeRisk(DoRiskAnalysisRequest request){

        return riskAnalysisFunction.analyze(request);

    }



    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setRiskAnalysisFunction(RiskAnalysisFunction riskAnalysisFunction) {
        this.riskAnalysisFunction = riskAnalysisFunction;
    }
}
