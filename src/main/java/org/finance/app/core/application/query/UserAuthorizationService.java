package org.finance.app.core.application.query;

import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.core.ddd.annotation.ApplicationService;
import org.finance.app.sharedcore.objects.Client;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component("UserAuthorizationService")
@ApplicationService
public class UserAuthorizationService {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Long getOrCreateClient(FormJSON formJSON){
        Long resultId;
        String clientFirstName = formJSON.getFirstName();
        String clientLastName = formJSON.getLastName();
        String clientAddress = formJSON.getAddress();

        String selectClientsWhich = "from Client c where " +
                "c.lastName=:clientLastName AND " +
                "c.firstName=:clientFirstName  AND " +
                "c.address=:clientAddress " ;

        Query selectSpecifiedClient = entityManager.createQuery(selectClientsWhich)
                .setParameter("clientLastName", clientLastName)
                .setParameter("clientFirstName", clientFirstName)
                .setParameter("clientAddress", clientAddress);

        try {
            Client existingClient = (Client) selectSpecifiedClient.getSingleResult();
            resultId = existingClient.getClientId();
        } catch (NoResultException noResultException) {
            Client client = new Client(clientFirstName, clientLastName, clientAddress);
            entityManager.persist(client);
            resultId = client.getClientId();
        }

        return resultId;
    }

}
