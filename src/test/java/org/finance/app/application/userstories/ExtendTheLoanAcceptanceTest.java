package org.finance.app.application.userstories;


import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.bports.services.LoanService;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.sharedcore.objects.LoanContract;
import org.finance.test.ConfigTest;
import org.finance.test.builders.PersonalDataBuilder;
import org.finance.test.builders.contracts.LoanContractBuilder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class ExtendTheLoanAcceptanceTest {

    @Autowired
    LoanService loanService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void currentUserShouldExtendHisLoanFor7Days(){

        //Given
        LoanContract newContract = new LoanContractBuilder().withDefaultLoan().withUniqueHolder().build();
        entityManager.persist(newContract);

        Long loanIdToExtend = newContract.getLatestGrantedLoan().getLoanId();
        Long contractHolderId = newContract.getContractHolder().getClientId();
        DateTime newExpirationDate = new DateTime(newContract.getLatestGrantedLoan().getExpirationDate()).plusDays(7);
        entityManager.flush();
        //When
        loanService.extendTheLoan(loanIdToExtend, contractHolderId, newExpirationDate);

        //Then
        assertInRepositoryExistNewLoanPeriod(loanIdToExtend, newContract.getContractHolder(), newExpirationDate);
    }

    @Transactional
    private void assertInRepositoryExistNewLoanPeriod(Long loanIdToExtend, Client contractHolder, DateTime newExpirationDate) {

        Query selectSLoans = entityManager.createQuery("from Loan l");

        List loans = selectSLoans.getResultList();
        System.out.println(loans.size());
        Query selectSingleLoan = entityManager.createQuery("from Loan l where l.basedOnLoanId=:basedOnLoan AND " +
                                                                            " l.loanHolder=:loanHolder AND " +
                                                                            " l.expirationDate=:newExpirationDate")
                .setParameter("basedOnLoan", loanIdToExtend)
                .setParameter("loanHolder", contractHolder)
                .setParameter("newExpirationDate", newExpirationDate);

        Loan extendedLoan =  (Loan) selectSingleLoan.getSingleResult();

        Assert.assertNotNull(extendedLoan);
    }


}
