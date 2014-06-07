package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.saga.SagaManager;
import org.finance.app.ddd.annotation.LoadSaga;
import org.finance.app.ddd.system.DomainEventPublisher;
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

    public GrantingOfLoanData createNewSagaData(AggregateId id) {
        GrantingOfLoanData sagaData = new GrantingOfLoanData();
        sagaData.setRequestId(id);
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

    public void handleRequestSubmittedEvent(){

    }

    @PostConstruct
    public void registerEventHandler() {

        try {
            Method method = GrantingOfLoanSagaManager.class.getMethod("handle", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler(RequestWasSubmitted.class, "GrantingOfLoanSagaManager", method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }


    public void handle(Object event) {
        //Request handled:
        //new saga created or existing is loading

    }

    @Autowired
    public GrantingOfLoanSagaManager(DomainEventPublisher eventPublisher, ApplicationContext applicationContext) {
        this.eventPublisher = eventPublisher;
        this.applicationContext = applicationContext;
    }
}
