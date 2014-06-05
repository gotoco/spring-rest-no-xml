package org.finance.app.core.domain.events.impl.customerservice;

import org.finance.app.core.domain.common.Loan;
import org.finance.app.ddd.annotation.Event;

import java.io.Serializable;

@Event
public class ExtendTheLoanRequest implements Serializable {

    private Loan basedLoan;

    public ExtendTheLoanRequest(Loan loan) {
        basedLoan = loan;
    }
}
