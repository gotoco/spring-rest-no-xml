package org.finance.app.core.application.query;

import junit.framework.Assert;
import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.application.parent.UserBaseTest;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.spring.WebAppNew;
import org.finance.app.spring.WebConfig;
import org.finance.app.spring.WebInit;
import org.finance.test.ConfigTest;
import org.finance.test.builders.PersonalDataBuilder;
import org.finance.test.builders.external.FormJSONBuilder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.math.BigDecimal;
import java.util.List;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(
        classes = {ConfigTest.class, WebAppNew.class, WebConfig.class, WebInit.class })
@Transactional
public class UserAuthorizationServiceTest extends UserBaseTest{

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Rollback(true)
    public void shouldGetExistingClient() throws Exception {
        //Given
        Client newClient = createSaveAndGetNewClient();
        FormJSON form = createFormForUser(newClient);
        //When
        Long clientIdFromRepo = userAuthorizationService.getOrCreateClient(form);
        //Then
        Assert.assertEquals(newClient.getClientId(), clientIdFromRepo);
    }

    @Test
    @Rollback(true)
    public void shouldCreateNewClient() throws Exception {
  /*      //Given
        Client newClient = createSaveAndGetNewClient();
        FormJSON form = createFormForUser(newClient);
        checkIfClientExistInDb(newClient);
        //When

        //Then*/
    }

}