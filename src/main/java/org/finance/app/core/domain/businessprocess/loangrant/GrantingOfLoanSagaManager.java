package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.impl.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.saga.SagaManager;
import org.finance.app.ddd.annotation.LoadSaga;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class GrantingOfLoanSagaManager implements
        SagaManager<GrantingOfLoanSaga, GrantingOfLoanData> {

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
}
