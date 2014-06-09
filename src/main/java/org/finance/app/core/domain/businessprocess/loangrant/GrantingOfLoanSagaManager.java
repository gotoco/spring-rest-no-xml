package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.events.impl.saga.IpCheckedResponse;
import org.finance.app.core.domain.events.impl.saga.RiskAnalyzedResponse;
import org.finance.app.core.domain.saga.SagaManager;
import org.finance.app.ddd.annotation.LoadSaga;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Method;

@Component("GrantingOfLoanSagaManager")
public class GrantingOfLoanSagaManager implements
        SagaManager<GrantingOfLoanSaga, GrantingOfLoanData> {

    private final DomainEventPublisher eventPublisher;

    private ApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @LoadSaga
    public GrantingOfLoanData loadSaga(RequestWasSubmitted event) {
        return findByRequestId(event.getRequestId());
    }

    @LoadSaga
    public GrantingOfLoanData loadSaga(ExtendTheLoanRequest event) {
        return findByLoanId(event.getLoanId());
    }

    @Override
    public void removeSaga(GrantingOfLoanSaga saga) {
        GrantingOfLoanData sagaData = entityManager.merge(saga.getData());
        entityManager.remove(sagaData);
    }

    @Override
    public GrantingOfLoanData createNewSagaData() {
        GrantingOfLoanData sagaData = new GrantingOfLoanData();
        entityManager.persist(sagaData);
        return sagaData;
    }

    public GrantingOfLoanData createNewSagaData(AggregateId id ) {
        GrantingOfLoanData sagaData = new GrantingOfLoanData();
        sagaData.setRequestId(id);
        entityManager.persist(sagaData);
        return sagaData;
    }

    public GrantingOfLoanData createAndFillNewSagaData(AggregateId id, RequestWasSubmitted requestEvent) {
        GrantingOfLoanData sagaData = new GrantingOfLoanData();
        sagaData.setRequestId(id);
        sagaData.fillDataFromForm(requestEvent.getRequest());
        entityManager.persist(sagaData);
        return sagaData;
    }

    public GrantingOfLoanData createAndFillNewSagaData(AggregateId id, ExtendTheLoanRequest requestEvent) {
        GrantingOfLoanData sagaData = new GrantingOfLoanData();
        sagaData.setRequestId(id);
        sagaData.fillDataFromRequest(requestEvent);
        entityManager.persist(sagaData);
        return sagaData;
    }


    private GrantingOfLoanData findByRequestId(AggregateId requestId) {
        Query query = entityManager.createQuery("from GrantingOfLoanData where requestId=:requestId")
                .setParameter("requestId", requestId);
        return (GrantingOfLoanData) query.getSingleResult();
    }

    private GrantingOfLoanData findByLoanId(Long loanId) {
        Query query = entityManager.createQuery("from GrantingOfLoanData where loanId=:loanId")
                .setParameter("loanId", loanId);
        return (GrantingOfLoanData) query.getSingleResult();
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
            Method method = GrantingOfLoanSagaManager.class.getMethod("handleRequestWasSubmitted", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (RequestWasSubmitted.class, "GrantingOfLoanSagaManager", method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private void registerExtendLoanRequestHandler(){
        try {
            Method method = GrantingOfLoanSagaManager.class.getMethod("handleExtendTheLoanRequest", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (ExtendTheLoanRequest.class, "GrantingOfLoanSagaManager", method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private void registerForCheckedIpEvent(){
        try {
            Method method = GrantingOfLoanSagaManager.class.getMethod("handleCheckedIpEvent", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (IpCheckedResponse.class, "GrantingOfLoanSagaManager", method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private void registerForRiskAnalyzedEvent(){
        try {
            Method method = GrantingOfLoanSagaManager.class.getMethod("handleRiskAnalyzedEvent", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (RiskAnalyzedResponse.class, "GrantingOfLoanSagaManager", method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    public void handleRequestWasSubmitted(Object event) {

        RequestWasSubmitted requestEvent = (RequestWasSubmitted)event;
        GrantingOfLoanData sagaData = null;
        try{
            sagaData = loadSaga(requestEvent);
        }catch(Exception ex){
            sagaData = createAndFillNewSagaData(requestEvent.getRequestId(), requestEvent);
        }

        GrantingOfLoanSaga saga = (GrantingOfLoanSaga)applicationContext.getBean("GrantingOfLoanSaga", sagaData);

        saga.completeLoanRequest();
    }

    public void handleExtendTheLoanRequest(Object event){
        ExtendTheLoanRequest requestEvent = (ExtendTheLoanRequest)event;
        GrantingOfLoanData sagaData = null;
        try{
            sagaData = loadSaga(requestEvent);
        }catch(Exception ex){
            sagaData = createAndFillNewSagaData(requestEvent.getAggregateId(), requestEvent);
        }

        GrantingOfLoanSaga saga = (GrantingOfLoanSaga)applicationContext.getBean("GrantingOfLoanSaga", sagaData);

        saga.completeExtendsLoan();
    }

    public void handleCheckedIpEvent(Object event){
        IpCheckedResponse response = (IpCheckedResponse)event;

        GrantingOfLoanData sagaData = findByRequestId(response.getSagaDataId());
        GrantingOfLoanSaga saga = (GrantingOfLoanSaga)applicationContext.getBean("GrantingOfLoanSaga", sagaData);

        saga.completeCheckIp();
    }

    public void handleRiskAnalyzedEvent(Object event){
        RiskAnalyzedResponse response = (RiskAnalyzedResponse)event;

        GrantingOfLoanData sagaData = findByRequestId(response.getSagaDataId());
        GrantingOfLoanSaga saga = (GrantingOfLoanSaga)applicationContext.getBean("GrantingOfLoanSaga", sagaData);

        saga.completeRiskAnalysis();
    }


    @Autowired
    public GrantingOfLoanSagaManager(DomainEventPublisher eventPublisher, ApplicationContext applicationContext) {
        this.eventPublisher = eventPublisher;
        this.applicationContext = applicationContext;
    }
}
