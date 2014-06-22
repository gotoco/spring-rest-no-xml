package org.finance.app.core.domain;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.domain.events.customerservice.ExtendTheLoanRequest;
import org.finance.app.core.domain.events.loanservice.LoanGrantedConfirmation;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.sharedcore.objects.LoanContract;
import org.finance.test.ConfigTest;
import org.finance.test.builders.contracts.LoanContractBuilder;
import org.finance.test.builders.events.ExtendTheLoanRequestBuilder;
import org.finance.test.builders.events.LoanGrantedConfirmationBuilder;
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
import javax.persistence.Query;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class LoanServiceTest {

    @Autowired
    private LoanService loanService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(true)
    public void shouldGrantNewLoanForEvent(){
        //Given
        LoanGrantedConfirmation loanConfirmation = new LoanGrantedConfirmationBuilder().withDefaultValues().build();

        //When
        loanService.decideToGrantLoan(loanConfirmation);

        //Then
        assertLoanCreated(loanConfirmation);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void shouldExtendLoanForEvent(){
        //Given
        LoanContract baseContract = new LoanContractBuilder().withDefaultData().build();
        Loan loanToExtend = baseContract.getLatestGrantedLoan();
        entityManager.persist(loanToExtend);
        ExtendTheLoanRequest request = new ExtendTheLoanRequestBuilder().withLoanFor7Days(loanToExtend).build();

        //When
        loanService.handleExtendALoanRequest(request);

        //Then
        assertLoanExtended(request);
    }

    public void assertLoanCreated(LoanGrantedConfirmation confirmation){
        String selectLoan = "from Loan l where " +
                "l.value=:value AND " +
                "l.effectiveDate=:effectiveDate  AND " +
                "l.expirationDate=:expirationDate  AND " +
                "l.loanHolder=:loanHolder " ;

        Query selectSpecifiedLoan = entityManager.createQuery(selectLoan)
                .setParameter("loanHolder", confirmation.getClient())
                .setParameter("effectiveDate", confirmation.getEffectiveDate().toDate())
                .setParameter("expirationDate", confirmation.getExpirationDate().toDate())
                .setParameter("value", confirmation.getValue());

        Loan specifiedLoan = (Loan) selectSpecifiedLoan.getSingleResult();

        Assert.assertNotNull(specifiedLoan);
    }

    public void assertLoanExtended(ExtendTheLoanRequest request){
        String selectLoan = "from Loan l where " +
                "l.expirationDate=:expirationDate AND " +
                "l.basedOnLoanId=:basedOnLoanId  " ;

        Query selectSpecifiedLoan = entityManager.createQuery(selectLoan)
                .setParameter("expirationDate", request.getNewExpirationDate().toDate())
                .setParameter("basedOnLoanId", request.getLoanId());

        Loan specifiedLoan = (Loan) selectSpecifiedLoan.getSingleResult();

        Assert.assertNotNull(specifiedLoan);
    }
}