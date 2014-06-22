package org.finance.app.bports.crudes;


import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Loan;

public interface LoanReaderService {

    public Loan getLoanByLoanIdAndUserId(Long loanId, Client client);
}
