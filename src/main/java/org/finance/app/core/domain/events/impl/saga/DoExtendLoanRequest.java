package org.finance.app.core.domain.events.impl.saga;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.common.Money;
import org.finance.app.core.domain.common.loan.Loan;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by maciek on 08.06.14.
 */
public class DoExtendLoanRequest implements Serializable {

    private final Long sagaDataId;

    private final Loan baseLoan;

    private final Date newExpirationDate;

    private final AggregateId aggregateId;

    private final Money value;


    public DoExtendLoanRequest(Long sagaDataId, Loan baseLoan, DateTime expirationDate, AggregateId id, Money value){
        this.sagaDataId = sagaDataId;
        this.baseLoan = baseLoan;
        this.newExpirationDate = expirationDate.toDate();
        this.aggregateId = id;
        this.value = value;
    }

}
