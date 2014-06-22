package org.finance.app.core.application.query;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.sharedcore.objects.LoanContract;
import org.finance.test.ConfigTest;
import org.finance.test.builders.contracts.LoanContractBuilder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class SimpleLoanQueryServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SimpleLoanQueryService simpleLoanQueryService;

    @Test
    @Transactional
    @Rollback(true)
    public void testGetLoanByLoanIdAndUserId() throws Exception {
        //Given
        Loan newLoan = prepareAndSaveFullLoanContract();
        //When
        Loan loanFromDb = simpleLoanQueryService.getLoanByLoanIdAndUserId(newLoan.getLoanId(), newLoan.getLoanHolder());
        //Then
        Assert.assertEquals(newLoan, loanFromDb);
    }

    @Transactional
    private Loan prepareAndSaveFullLoanContract(){
        LoanContract contract = new LoanContractBuilder().withDefaultData().build();
        entityManager.persist(contract);
        return contract.getLoanPeriods().get(0);
    }

}