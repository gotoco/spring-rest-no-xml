package org.finance.app.core.application.closure.loan;

import org.finance.app.core.domain.common.loan.ExtendTheLoanFunction;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.core.ddd.annotation.Function;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("basicExtendTheLoan")
public class BasicExtendTheLoan implements ExtendTheLoanFunction{

    private final static BigDecimal extendFactor = new BigDecimal(1.5);
    private final static BigDecimal interestRate = new BigDecimal(0.2);

    @Function
    @Override
    public Loan extend (Loan oldLoan, DateTime newExpirationDate){
        Loan loan = new Loan();
        loan.setBasedOnLoan(oldLoan.getLoanId());
        loan.setExpirationDate(newExpirationDate.toDate());
        loan.setEffectiveDate(new DateTime().toDate());
        loan.setInterest(calculateInterests(oldLoan));
        loan.setValue(oldLoan.getValue());
        loan.setLoanHolder(oldLoan.getLoanHolder());
        loan.setContract(oldLoan.getContract());

        return loan;
    }


    private Money calculateInterests(Loan loan){
        Money calculatedInterests;
        Money actualInterests = loan.getInterest();

        if(actualInterests == null || actualInterests.equals(new Money(0))){
            calculatedInterests = loan.getValue().multiplyBy(interestRate);
        }
        else {
            calculatedInterests = actualInterests.multiplyBy(extendFactor);
        }
        return calculatedInterests;
    }
}
