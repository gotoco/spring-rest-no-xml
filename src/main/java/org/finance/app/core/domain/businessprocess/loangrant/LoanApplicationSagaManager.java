package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.events.saga.IpCheckedResponse;
import org.finance.app.core.domain.events.saga.RiskAnalyzedResponse;
import org.finance.app.core.domain.saga.LoanSagaManager;
import org.finance.app.core.ddd.annotation.LoadSaga;
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

@Component("loanApplicationSagaManager")
public class LoanApplicationSagaManager implements
        LoanSagaManager<LoanApplicationSaga, LoanApplicationData> {

    private static final String loanApplicationSagaName = "loanApplicationSaga";
    private static final String grantingOfLoanSagaManagerName = "loanApplicationSagaManager";

    private DomainEventPublisher eventPublisher;

    private ApplicationContext applicationContext;

    private EntityManager entityManager;

    @PersistenceContext
    void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setEventPublisher(DomainEventPublisher eventPublisher){
        this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @LoadSaga
    public LoanApplicationData loadSaga(RequestWasSubmitted event) {
        return findByRequestId(event.getRequestId());
    }

    @LoadSaga
    public LoanApplicationData loadSaga(ExtendTheLoanRequest event) {
        return findByLoanId(event.getLoanId());
    }

    @Override
    @Transactional
    public void removeSaga(LoanApplicationSaga saga) {
        LoanApplicationData sagaData = entityManager.merge(saga.getData());
        entityManager.remove(sagaData);
    }

    @Override
    @Transactional
    public LoanApplicationData createNewSagaData() {
        LoanApplicationData sagaData = new LoanApplicationData();
        entityManager.persist(sagaData);
        return sagaData;
    }

    @Transactional
    public LoanApplicationData createNewSagaData(AggregateId id ) {
        LoanApplicationData sagaData = new LoanApplicationData(id);
        entityManager.persist(sagaData);
        return sagaData;
    }

    @Transactional
    public LoanApplicationData createAndFillNewSagaData(AggregateId id, RequestWasSubmitted requestEvent) {
        LoanApplicationData sagaData = new LoanApplicationData(id);
        sagaData.whenFormIsApplied(requestEvent.getRequest());
        entityManager.persist(sagaData);
        return sagaData;
    }

    @Transactional
    public LoanApplicationData createAndFillNewSagaData(AggregateId id, ExtendTheLoanRequest requestEvent) {
        LoanApplicationData sagaData = new LoanApplicationData(id);
        sagaData.fillDataFromRequest(requestEvent);
        entityManager.merge(sagaData.getLoan());
        sagaData.updateLoanInformation(entityManager.merge(sagaData.getLoan()));
        entityManager.persist(sagaData);
        return sagaData;
    }

    private LoanApplicationData findByRequestId(AggregateId requestId) {
        Query query = entityManager.createQuery("from LoanApplicationData where requestId=:requestId")
                .setParameter("requestId", requestId);
        return (LoanApplicationData) query.getSingleResult();
    }

    private LoanApplicationData findByLoanId(Long loanId) {
        Query query = entityManager.createQuery("from LoanApplicationData where loanId=:loanId")
                .setParameter("loanId", loanId);
        return (LoanApplicationData) query.getSingleResult();
    }

    @PostConstruct
    public void registerEventHandler() {
        registerSubmittedRequestHandler();
        registerExtendLoanRequestHandler();
        registerForRiskAnalyzedEvent();
        registerForCheckedIpEvent();
    }

    private void registerSubmittedRequestHandler(){
        try {
            Method method = LoanSagaManager.class.getMethod("handleRequestWasSubmitted", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (RequestWasSubmitted.class, grantingOfLoanSagaManagerName, method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private void registerExtendLoanRequestHandler(){
        try {
            Method method = LoanSagaManager.class.getMethod("handleExtendTheLoanRequest", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (ExtendTheLoanRequest.class, grantingOfLoanSagaManagerName, method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private void registerForCheckedIpEvent(){
        try {
            Method method = LoanSagaManager.class.getMethod("handleCheckedIpEvent", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (IpCheckedResponse.class, grantingOfLoanSagaManagerName, method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private void registerForRiskAnalyzedEvent(){
        try {
            Method method = LoanSagaManager.class.getMethod("handleRiskAnalyzedEvent", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (RiskAnalyzedResponse.class, grantingOfLoanSagaManagerName, method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    @Transactional
    public void handleRequestWasSubmitted(Object event) {

        RequestWasSubmitted requestEvent = (RequestWasSubmitted)event;
        LoanApplicationData sagaData;
        try{
            sagaData = loadSaga(requestEvent);
        }catch(Exception ex){
            sagaData = createAndFillNewSagaData(requestEvent.getRequestId(), requestEvent);
        }

        LoanApplicationSaga saga = (LoanApplicationSaga)applicationContext.getBean(loanApplicationSagaName, sagaData);

        saga.completeLoanRequest();
    }

    @Transactional
    public void handleExtendTheLoanRequest(Object event){
        ExtendTheLoanRequest requestEvent = (ExtendTheLoanRequest)event;
        LoanApplicationData sagaData;
        try{
            sagaData = loadSaga(requestEvent);
        }catch(Exception ex){
            sagaData = createAndFillNewSagaData(requestEvent.getAggregateId(), requestEvent);
        }

        LoanApplicationSaga saga = (LoanApplicationSaga)applicationContext.getBean(loanApplicationSagaName, sagaData);

        saga.completeExtendsLoan();
    }

    @Transactional
    public void handleCheckedIpEvent(Object event){
        IpCheckedResponse response = (IpCheckedResponse)event;

        LoanApplicationData sagaData = findByRequestId(response.getSagaDataId());
        LoanApplicationSaga saga = (LoanApplicationSaga)applicationContext.getBean(loanApplicationSagaName, sagaData);

        saga.completeCheckIp();
    }

    @Transactional
    public void handleRiskAnalyzedEvent(Object event){
        RiskAnalyzedResponse response = (RiskAnalyzedResponse)event;

        LoanApplicationData sagaData = findByRequestId(response.getSagaDataId());
        LoanApplicationSaga saga = (LoanApplicationSaga)applicationContext.getBean(loanApplicationSagaName, sagaData);

        saga.completeRiskAnalysis();
    }

}
