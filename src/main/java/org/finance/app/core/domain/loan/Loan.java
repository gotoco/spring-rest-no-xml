package org.finance.app.core.domain.loan;

import org.finance.app.core.domain.common.Money;
import org.finance.app.ddd.annotation.ValueObject;

/**
 * Created by maciek on 02.06.14.
 */
@ValueObject
public class Loan {

    private Loan basedOnLoan;

    private Money value;

    public Loan(){

    }

}
