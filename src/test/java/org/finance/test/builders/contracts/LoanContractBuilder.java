package org.finance.test.builders.contracts;

import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.sharedcore.objects.LoanContract;
import org.finance.test.builders.PersonalDataBuilder;
import org.finance.test.builders.loan.LoanBuilder;

import java.util.ArrayList;
import java.util.List;

public class LoanContractBuilder {

    private List<Loan> loanPeriods = new ArrayList<Loan>();
    private Client contractHolder;

    public LoanContractBuilder(List<Loan> loanPeriods, Client contractHolder){
        this.contractHolder = contractHolder;
        this.loanPeriods = loanPeriods;
    }

    public LoanContractBuilder() {

    }

    public LoanContractBuilder withDefaultHolder(){
        this.contractHolder = new PersonalDataBuilder().withDefaultData().build();
        return this;
    }

    public LoanContractBuilder withDefaultLoan(){
        LoanContract contract = this.build();
        Loan loan = new LoanBuilder().withDefaultData().build();
        this.loanPeriods.add(loan);
        return this;
    }

    public LoanContractBuilder withDefaultData(){
        return this.withDefaultHolder().withDefaultLoan();
    }

    public LoanContract build(){
        LoanContract result = new LoanContract(loanPeriods, contractHolder);
        result.getLoanPeriods().stream().forEach( (period) -> period.setContract(result));
        return result;
    }


}
