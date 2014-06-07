package org.finance.app.core.domain.common.loan;

import org.joda.time.DateTime;

public interface ExtendTheLoanFunction {

    public Loan extend(DateTime changeDate);

}
