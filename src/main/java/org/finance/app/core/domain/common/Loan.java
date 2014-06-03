package org.finance.app.core.domain.common;

import org.finance.app.ddd.annotation.ValueObject;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by maciek on 02.06.14.
 */
@ValueObject
public class Loan {

    private final static BigDecimal extendFactor = new BigDecimal(1.5);
    private final static Integer extendDays = 7;

    private Loan basedOnLoan;

    private Money value;

    private Money interest;

    private DateTime expirationDate;

    private DateTime effectiveDate;

    public Loan(Loan basedOnLoan, Money value, Money interest, DateTime expirationDate, DateTime effectiveDate) {
        this.basedOnLoan = basedOnLoan;
        this.value = value;
        this.interest = interest;
        this.expirationDate = expirationDate;
        this.effectiveDate = effectiveDate;
    }

    public Loan extendLoan(DateTime changeDate){

        if (changeDate.isBefore(effectiveDate) || changeDate.isAfter(expirationDate)) {
            throw new IllegalArgumentException("Time sequence fault. Incorrect data");
        }

        this.expirationDate = changeDate;
        Loan futureLoan = new Loan(this, value, interest.multiplyBy(extendFactor),
                                    expirationDate.plusDays(extendDays), changeDate );

        return futureLoan;
    }

    public void increaseLoanValue(Money money){
        this.value.add(money);
    }

    public void subtractLoanValue(Money money){
        this.value.subtract(money);
    }

    public void increaseInterestValue(Money money){
        this.interest.add(money);
    }

    public void subtractInterestValue(Money money){
        this.interest.subtract(money);
    }

    public Money showLoanValue() {
        return this.value;
    }

    public Money showInterestValue() {
        return this.interest;
    }

}
