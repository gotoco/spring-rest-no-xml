package org.finance.app.core.application.closure.loan;

import org.finance.app.core.domain.common.loan.ExtendTheLoanFunction;
import org.finance.app.core.domain.common.loan.Loan;
import org.finance.app.ddd.annotation.Function;
import org.joda.time.DateTime;

public class BasicExtendTheLoan implements ExtendTheLoanFunction{

    @Function
    @Override
    public Loan extend(DateTime changeDate) {
        return null;
    }
}
