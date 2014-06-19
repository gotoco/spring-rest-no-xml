package org.finance.app.core.application.parent;


import junit.framework.Assert;
import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.sharedcore.objects.Client;
import org.finance.test.builders.PersonalDataBuilder;
import org.finance.test.builders.external.FormJSONBuilder;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

public class UserBaseTest {

    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected Client createSaveAndGetNewClient(){
        Client client = new PersonalDataBuilder().withDefaultData().build();
        entityManager.persist(client);
        return client;
    }

    @Transactional
    protected void checkIfClientExistInDb(Client client){
        Query selectClient = entityManager.createQuery("from Client c where c.firstName=:firstName AND c.lastName=:lastName AND c.address=:address")
                .setParameter("firstName", client.getFirstName())
                .setParameter("lastName", client.getLastName())
                .setParameter("address", client.getAddress());
        List resultList = selectClient.getResultList();
        Assert.assertTrue(resultList.isEmpty());
    }

    protected FormJSON createFormForUser(Client client){
        return new FormJSONBuilder().withSpecifiedData(client.getFirstName(),
                client.getLastName(),
                new BigDecimal(1000),
                "1.1.1.1.1",
                30,
                new DateTime(),
                client.getAddress())
                .build();
    }
}
