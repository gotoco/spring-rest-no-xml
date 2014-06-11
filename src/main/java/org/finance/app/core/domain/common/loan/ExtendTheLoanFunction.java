package org.finance.app.core.domain.common.loan;

import org.finance.app.sharedcore.objects.Loan;
import org.joda.time.DateTime;

public interface ExtendTheLoanFunction {

    public Loan extend(Loan oldLoan, DateTime newExpirationDate);

}
