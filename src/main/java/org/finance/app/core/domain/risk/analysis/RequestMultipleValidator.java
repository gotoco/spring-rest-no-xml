package org.finance.app.core.domain.risk.analysis;


import org.finance.app.core.domain.businessprocess.loangrant.GrantingOfLoanData;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.customerservice.RequestWasSubmitted;
import org.finance.app.core.domain.events.impl.saga.CheckIpRequest;
import org.finance.app.core.domain.events.impl.saga.DoRiskAnalysisRequest;
import org.finance.app.core.domain.events.impl.saga.IpCheckedResponse;
import org.finance.app.core.domain.risk.RiskAnalysisFunction;
import org.finance.app.ddd.annotation.DomainService;
import org.finance.app.ddd.system.DomainEventPublisher;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.lang.reflect.Method;

@DomainService
@Component("RequestMultipleValidator")
public class RequestMultipleValidator {

    private static final Integer permittedNumberOfSubmissionsFromSingleNumber = 3;

    @PersistenceContext
    EntityManager entityManager;

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
            Method method = RequestMultipleValidator.class.getMethod("validateIpAddress", new Class[]{CheckIpRequest.class});
            SpringEventHandler eventHandler = new SpringEventHandler(CheckIpRequest.class, "RequestMultipleValidator", method, applicationContext);
            eventPublisher.registerEventHandler(eventHandler);
        } catch(NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    public void validateIpAddress(CheckIpRequest event){
        AggregateId eventId = event.getSagaDataId();
        String ipAddress = event.getAddressIp();
        DateTime startDate = event.getSubmissionDate();

        Boolean isValid = validate(eventId, ipAddress, startDate);

        eventPublisher.publish(new IpCheckedResponse(eventId, isValid));
    }

    @Transactional
    private synchronized Boolean validate(AggregateId eventId, String ipAddress, DateTime startDate){
        String queryBase = "FROM GrantingOfLoanData g WHERE g.dateOfApplication > :startDate AND g.ip = :ipAddress AND g.hasValidIp IS NOT NULL";
        Boolean result = null;

        Query eventsFromThisDayWithSameIp = entityManager.createQuery(queryBase)
                    .setParameter("startDate", startDate.toDate(), TemporalType.DATE)
                    .setParameter("ipAddress", ipAddress);
        Integer similarEventsCount = eventsFromThisDayWithSameIp.getResultList().size();
        result = similarEventsCount < permittedNumberOfSubmissionsFromSingleNumber;

        Query selectEntityToUpdate = entityManager.createQuery("from GrantingOfLoanData where requestId=:requestId")
                                                  .setParameter("requestId", eventId);
        GrantingOfLoanData entityToUpdate = (GrantingOfLoanData) selectEntityToUpdate.getSingleResult();

        if(result){
            entityToUpdate.setHasValidIp(true);
        } else {
            entityToUpdate.setHasValidIp(false);
        }

        entityManager.persist(entityToUpdate);

        return result;
    }
}
