package org.finance.app.core.application.query;

import com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet;
import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.bports.crudes.ClientReaderService;
import org.finance.app.bports.crudes.ContractSchedulerPort;
import org.finance.app.sharedcore.objects.Client;
import org.finance.test.builders.PersonalDataBuilder;
import org.finance.test.ConfigTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import static junit.framework.TestCase.assertNull;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class ClientFinderTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ContractSchedulerPort historyBrowsingService;

    @Autowired
    ClientReaderService clientFinder;

    @Test
    @Transactional
    public void shouldFindSingleClientRecord() throws Exception {
        //Given
        Client newClient = createAndSaveClientRecordToDb();

        //When
        Client clientFromDB = clientFinder.findClientById(newClient.getClientId());

        //Then
        Assert.assertEquals(clientFromDB, newClient);
    }

    @Test
    @Transactional
    public void whenClientNotFound(){
        //Given
        Client newClient = createAndSaveClientRecordToDb();
        Long randomId = newClient.getClientId()+11;
        Client clientFromDB = null;

        try {
        //When
        clientFromDB = clientFinder.findClientById(randomId);
            Assert.fail();
        } catch (NoResultException noResultException){

        //Then
        assertNull(clientFromDB);
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private Client createAndSaveClientRecordToDb(){
        Client client = new PersonalDataBuilder().withDefaultData().build();

        entityManager.persist(client);

        return client;
    }
}