package org.finance.app.core.application.query;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.bports.crudes.ContractSchedulerPort;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.LoanContract;
import org.finance.test.ConfigTest;
import org.finance.test.builders.contracts.LoanContractBuilder;
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
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class HistoryBrowsingServiceTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ContractSchedulerPort historyBrowsingService;

    @Test
    @Transactional
    public void testGetAllContractsOfUser() throws Exception {

        //given
        LoanContract loanContracts = createAndSaveContracts();
        Client client = loanContracts.getContractHolder();

        //when
        List<LoanContract> savedContracts = historyBrowsingService.getAllContractsOfUser(client);

        //then
        assertTrue(savedContracts.size() == 1);
        assertContractsAreEqual(savedContracts.get(0), loanContracts);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private LoanContract createAndSaveContracts() {
        LoanContract loanContract = new LoanContractBuilder().withDefaultData().build();

        entityManager.persist(loanContract);
        entityManager.flush();

        return loanContract;
    }

    private void assertContractsAreEqual(LoanContract contract1, LoanContract contract2){
        Assert.assertTrue(compareWithDependencies(contract1, contract2));
    }

    private Boolean compareWithDependencies(LoanContract contract1, LoanContract contract2){
        Boolean result =
            contract1.getContractHolder().equals(contract2.getContractHolder())
            &&
            contract1.getLoanPeriods().stream().allMatch( p -> contract2.getLoanPeriods().contains(p));

        return result;
    }
}