package org.finance.app.core.application.query;

import org.finance.app.core.ddd.annotation.ApplicationService;
import org.finance.app.bports.crudes.ClientReaderService;
import org.finance.app.sharedcore.objects.Client;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component("ClientFinder")
@ApplicationService
public class ClientFinder implements ClientReaderService {

    @PersistenceContext
    private EntityManager entityManager;

    public Client findClientById(Long clientId){
        Query query = entityManager.createQuery("from Client c where c.clientId=:requestId")
                .setParameter("requestId", clientId);

        return (Client) query.getSingleResult();
    }

}
