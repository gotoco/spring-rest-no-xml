package org.finance.app.ports.services;


import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Loan;


public interface LoanServiceApi {

    public void applyForLoan(Form form);

    public void extendTheLoan(Long loanId, Long clientId);
}
