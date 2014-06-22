package org.finance.app.core.application.closure.loan;

import junit.framework.Assert;
import org.finance.app.annotations.UnitTest;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.sharedcore.objects.Money;
import org.finance.test.builders.loan.LoanBuilder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class BasicExtendTheLoanTest {

    private static BasicExtendTheLoan function = new BasicExtendTheLoan();

    private final Money defaultInterestForLoanExtendFor7Days = new Money(150);

    @Test
    public void newPeriodShouldHaveSameSharedData(){

        //Given
        Loan baseLoan = new LoanBuilder().withDefaultData().build();

        //When
        Loan newLoan = function.extend(baseLoan, new DateTime().plusDays(20));

        //Then
        Assert.assertTrue(compareTwoLoansSharedDataLoan(newLoan, baseLoan));

    }

    @Test
    public void testCorrectnessOfChangedData(){

        //Given
        Loan baseLoan = new LoanBuilder().withDefaultData().withExpirationDate(new DateTime()).build();
        DateTime a7daysAgo = new DateTime().plusDays(7);
        //When
        Loan newLoan = function.extend(baseLoan, a7daysAgo);

        //Then
        Assert.assertTrue(checkChangedDataBasedOnOldPeriod(newLoan, baseLoan, a7daysAgo));

    }

    private Boolean compareTwoLoansSharedDataLoan(Loan newLoan, Loan oldLoan){

        Assert.assertTrue(newLoan.getLoanHolder().equals(oldLoan.getLoanHolder()) );
        Assert.assertTrue(newLoan.getValue().equals(oldLoan.getValue()));

        return true;
    }

    private Boolean checkChangedDataBasedOnOldPeriod(Loan newLoan, Loan oldLoan, DateTime newExpirationDate){
        Assert.assertTrue(newLoan.getInterest().equals(defaultInterestForLoanExtendFor7Days) );
        Assert.assertTrue(newLoan.getExpirationDate().equals(newExpirationDate.toDate()));

        return true;
    }

}