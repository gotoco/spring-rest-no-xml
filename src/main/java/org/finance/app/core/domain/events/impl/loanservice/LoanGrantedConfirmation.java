package org.finance.app.core.domain.events.impl.loanservice;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.ddd.annotation.Event;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;

import java.io.Serializable;

@Event
public class LoanGrantedConfirmation implements Serializable {

    private final AggregateId aggregateId;

    private final Client client;
    private final Money value;
    private final DateTime expirationDate;
    private final DateTime effectiveDate;

    public LoanGrantedConfirmation(AggregateId aggregateId, Client client, Money value, DateTime expirationDate, DateTime effectiveDate) {
        this.aggregateId = aggregateId;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.value = value;
        this.client = client;
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }

    public DateTime getEffectiveDate() {
        return effectiveDate;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public Money getValue() {
        return value;
    }

    public Client getClient() {
        return client;
    }
}
