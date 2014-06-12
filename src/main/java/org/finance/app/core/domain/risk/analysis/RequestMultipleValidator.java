package org.finance.app.core.domain.risk.analysis;


import org.finance.app.core.domain.businessprocess.loangrant.LoanApplicationData;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.saga.CheckIpRequest;
import org.finance.app.core.domain.events.saga.IpCheckedResponse;
import org.finance.app.core.ddd.annotation.DomainService;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.lang.reflect.Method;

@DomainService
@Component("requestMultipleValidator")
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
            SpringEventHandler eventHandler = new SpringEventHandler(CheckIpRequest.class, "requestMultipleValidator", method, applicationContext);
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private synchronized Boolean validate(AggregateId eventId, String ipAddress, DateTime startDate){
        String queryBase = "FROM LoanApplicationData g WHERE g.dateOfApplication > :startDate AND g.ip = :ipAddress AND g.hasValidIp IS NOT NULL";
        Boolean result;

        Query eventsFromThisDayWithSameIp = entityManager.createQuery(queryBase)
                    .setParameter("startDate", startDate.toDate(), TemporalType.DATE)
                    .setParameter("ipAddress", ipAddress);
        Integer similarEventsCount = eventsFromThisDayWithSameIp.getResultList().size();
        result = similarEventsCount < permittedNumberOfSubmissionsFromSingleNumber;

        Query selectEntityToUpdate = entityManager.createQuery("from LoanApplicationData where requestId=:requestId")
                                                  .setParameter("requestId", eventId);
        LoanApplicationData entityToUpdate = (LoanApplicationData) selectEntityToUpdate.getSingleResult();

        IpCheckedResponse response = new IpCheckedResponse(eventId, result);

        entityToUpdate.whenIpValidIsChecked(response);

        entityManager.persist(entityToUpdate);

        return result;
    }
}
