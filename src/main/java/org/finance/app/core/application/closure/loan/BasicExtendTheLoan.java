package org.finance.app.core.application.closure.loan;

import org.finance.app.core.ddd.annotation.Function;
import org.finance.app.core.domain.common.loan.ExtendTheLoanFunction;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("basicExtendTheLoan")
public class BasicExtendTheLoan implements ExtendTheLoanFunction{

    public final static BigDecimal extendFactor = new BigDecimal(1.5);
    public final static BigDecimal interestRate = new BigDecimal(0.2);

    @Function
    @Override
    public Loan extend (Loan oldLoan, DateTime newExpirationDate){
        Integer calculatedNumberOfWeeks = Days.daysBetween(new DateTime(oldLoan.getExpirationDate()), newExpirationDate).getDays()/7;
        Loan loan = new Loan();
        loan.setBasedOnLoan(oldLoan.getLoanId());
        loan.setExpirationDate(newExpirationDate.toDate());
        loan.setEffectiveDate(new DateTime().toDate());
        loan.setInterest(calculateInterests(oldLoan, calculatedNumberOfWeeks));
        loan.setValue(oldLoan.getValue());
        loan.setLoanHolder(oldLoan.getLoanHolder());
        loan.setContract(oldLoan.getContract());

        return loan;
    }


    private Money calculateInterests(Loan loan, Integer weeks){
        Money calculatedInterests;
        Money actualInterests = loan.getInterest();

        if(actualInterests == null || actualInterests.equals(new Money(0))){
            calculatedInterests = loan.getValue().multiplyBy(interestRate);
        }
        else {
             calculatedInterests = actualInterests;
             for(int i=0; i<weeks; i++){
                 calculatedInterests = calculatedInterests.multiplyBy(extendFactor);
             }
        }
        return calculatedInterests;
    }
}
