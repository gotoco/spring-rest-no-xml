package org.finance.app.core.domain.risk.analysis;

import org.finance.app.core.domain.businessprocess.loangrant.LoanApplicationData;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.saga.DoRiskAnalysisRequest;
import org.finance.app.core.domain.events.saga.RiskAnalyzedResponse;
import org.finance.app.core.domain.risk.Risk;
import org.finance.app.core.domain.risk.RiskAnalysisFunction;
import org.finance.app.core.ddd.annotation.DomainService;
import org.finance.app.core.ddd.system.DomainEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Method;
import java.util.*;

@DomainService
@Component("riskAnalysisService")
public class RiskAnalysisService {

    private final DomainEventPublisher eventPublisher;

    private ApplicationContext applicationContext;

    RiskAnalysisFunction riskAnalysisFunction;

    private final List<Risk> allRisks;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public RiskAnalysisService(DomainEventPublisher eventPublisher, ApplicationContext applicationContext) {
        this.eventPublisher = eventPublisher;
        this.applicationContext = applicationContext;

        allRisks = getAllRisksFromRepository();
    }

    @PostConstruct
    public void registerEventHandler() {
        try {
            Method method = RiskAnalysisService.class.getMethod("analyzeRisk", new Class[]{DoRiskAnalysisRequest.class});

            SpringEventHandler eventHandler = new SpringEventHandler(DoRiskAnalysisRequest.class, "riskAnalysisService", method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    @Transactional
    private void doAnalyze(AggregateId eventId, List<Risk> risks){
        Query selectEntityToUpdate = entityManager.createQuery("from LoanApplicationData where requestId=:requestId")
                .setParameter("requestId", eventId);
        LoanApplicationData entityToUpdate = (LoanApplicationData) selectEntityToUpdate.getSingleResult();

        RiskAnalyzedResponse response = new RiskAnalyzedResponse(eventId, risks);

        entityToUpdate.whenRiskWasAnalyzed(response) ;

        entityManager.persist(entityToUpdate);

        eventPublisher.publish(response);
    }


    public void analyzeRisk(DoRiskAnalysisRequest request){
        List<Risk> risks = riskAnalysisFunction.analyze(request, allRisks);

        doAnalyze(request.getSagaDataId(), risks);
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setRiskAnalysisFunction(RiskAnalysisFunction riskAnalysisFunction) {
        this.riskAnalysisFunction = riskAnalysisFunction;
    }

    @Transactional
    public List<Risk> getAllRisksFromRepository() {
        try {
            List allRisks = entityManager.createQuery("from Risk").getResultList();

            return new ArrayList<Risk>(allRisks);
        }catch(Exception ex){
            List allRisks = new ArrayList<Risk>();
            allRisks.add(new Risk("Cause"));

            return allRisks ;
        }


    }

    public List<Risk> getAllRisks(){
        return this.allRisks;
    }
}
