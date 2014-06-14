package org.finance.test.builders.loan;

import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.sharedcore.objects.LoanContract;
import org.finance.app.sharedcore.objects.Money;
import org.finance.test.builders.PersonalDataBuilder;
import org.joda.time.DateTime;

import java.util.Date;

public class LoanBuilder {

    private Money value;

    private Money interest;

    private Date expirationDate;

    private Date effectiveDate;

    private Client loanHolder;

    private LoanContract loanContract;

    private Long basedOnLoanId;

    public LoanBuilder(){

    }

    public LoanBuilder defaultWithoutContract(){
        this.value = new Money(3000);
        this.interest = new Money(0);
        this.effectiveDate = new DateTime().toDate();
        this.expirationDate = new DateTime().plusDays(30).toDate();
        this.loanHolder = new PersonalDataBuilder().withDefaultData().build();

        return this;
    }

    public LoanBuilder withDefaultData(){
        this.value = new Money(2500);
        this.interest = new Money(100);
        this.effectiveDate = new DateTime().toDate();
        this.expirationDate = new DateTime().plusDays(30).toDate();
        this.loanHolder = new PersonalDataBuilder().withDefaultData().build();

        return this;
    }

    public LoanBuilder withEffectiveDate(DateTime date){
        this.effectiveDate = date.toDate();

        return this;
    }

    public Loan build(){

        return new Loan(1L,
                        value,
                        interest,
                        new DateTime(expirationDate),
                        new DateTime(effectiveDate),
                        loanHolder);

    }
}
