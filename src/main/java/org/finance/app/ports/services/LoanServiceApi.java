package org.finance.app.ports.services;


import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.common.loan.Loan;


public interface LoanServiceApi {

    public void applyForLoan(Form form);

    public void extendTheLoan(Loan loan);
}
