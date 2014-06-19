package org.finance.app.core.application.applicationservices;

import junit.framework.Assert;
import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.bports.services.LoanService;
import org.finance.app.core.application.parent.UserBaseTest;
import org.finance.app.sharedcore.objects.Client;
import org.finance.test.ConfigTest;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class CustomerServiceFacadeTest extends UserBaseTest {

    @Autowired
    LoanService customerServiceFacade;

    @Test
    @Transactional
    @Rollback(true)
    public void whenCorrectSubmissionNewLoanIsCreated(){
        //Given
        Client newClient = createSaveAndGetNewClient();
        FormJSON form = getApplicationFormWithCorrectUser(newClient);

        //When
        customerServiceFacade.applyForLoan(form, new DateTime());

        //Then
        Assert.assertTrue(checkIfExistLoanForClient(newClient));
    }

    public FormJSON getApplicationFormWithCorrectUser(Client newClient){
        FormJSON form = createFormForUser(newClient);

        return form;
    }

    @Transactional
    public Boolean checkIfExistLoanForClient(Client client){
        String selectLoanWhereUser = "from Loan l where l.loanHolder = :client";

        Query selectSpecifiedLoan = entityManager.createQuery(selectLoanWhereUser)
                .setParameter("client", client);

        return !selectSpecifiedLoan.getResultList().isEmpty();
    }
}