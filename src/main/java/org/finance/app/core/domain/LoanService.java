package org.finance.app.core.domain;

import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.common.Loan;
import org.finance.app.core.domain.risk.Decision;
import org.finance.app.core.domain.risk.Risk;
import org.finance.app.ddd.annotation.AggregateRoot;

/**
 * Created by maciek on 02.06.14.
 */
@AggregateRoot
public class LoanService {

    public Risk analyzeRiskOfProposal(Form form){

        return null;
    }

    public Decision decideToGrantLoan(Form form){

        return null;
    }

    public void extendALoan(Loan loan){

    }

}
