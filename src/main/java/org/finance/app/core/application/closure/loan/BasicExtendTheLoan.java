package org.finance.app.core.application.closure.loan;

import org.finance.app.core.domain.common.loan.ExtendTheLoanFunction;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.core.ddd.annotation.Function;
import org.joda.time.DateTime;

public class BasicExtendTheLoan implements ExtendTheLoanFunction{

    @Function
    @Override
    public Loan extend (Loan oldLoan, DateTime newExpirationDate){
        Loan loan = new Loan();
        loan.setBasedOnLoan(oldLoan);
        loan.setExpirationDate(newExpirationDate.toDate());
        loan.setEffectiveDate(new DateTime().toDate());
/*        loan.setInterest();
        loan.setValue();
        loan.setLoanHolder();*/



        return loan;
    }
}
