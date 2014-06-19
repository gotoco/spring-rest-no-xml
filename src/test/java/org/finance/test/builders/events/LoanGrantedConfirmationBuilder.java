package org.finance.test.builders.events;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.loanservice.LoanGrantedConfirmation;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Money;
import org.finance.test.builders.PersonalDataBuilder;
import org.joda.time.DateTime;

public class LoanGrantedConfirmationBuilder {

    private AggregateId aggregateId;
    private Client client;
    private Money value;
    private DateTime expirationDate;
    private DateTime effectiveDate;

    public LoanGrantedConfirmationBuilder(){

    }

    public LoanGrantedConfirmationBuilder withDefaultValues(){
        aggregateId = AggregateId.generate();
        client = new PersonalDataBuilder().withDefaultData().build();
        value = new Money(2000);
        expirationDate = new DateTime().plusDays(30);
        effectiveDate = new DateTime();
        return this;
    }

    public LoanGrantedConfirmation build(){
        return new LoanGrantedConfirmation(aggregateId, client, value, expirationDate, effectiveDate);
    }
}
