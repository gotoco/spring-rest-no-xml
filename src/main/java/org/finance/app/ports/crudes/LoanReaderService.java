package org.finance.app.ports.crudes;


import org.finance.app.sharedcore.objects.Loan;

public interface LoanReaderService {

    public Loan getLoanByLoanIdAndUserId(Long loanId, Long userId);
}
