package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.annotations.IntegrationTest;
import org.finance.test.ConfigTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by maciek on 07.06.14.
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class GrantingOfLoanSagaManagerTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void newRequestSubmittedNewSagaCreated(){

        //Given
            //Create request

        //When
            //Request handled

        //Then
            //new saga created

    }

    @Test
    public void loanExistExtendLoanRequestExistingSagaLoaded(){
        //Given
            //Create and save loan
            //create request - extendLoanRequest

        //When
            //Handle request

        //Then
            //Existing saga loaded
    }

}
