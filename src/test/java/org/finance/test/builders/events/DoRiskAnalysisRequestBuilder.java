package org.finance.test.builders.events;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.events.saga.DoRiskAnalysisRequest;
import org.finance.app.sharedcore.objects.Money;
import org.joda.time.DateTime;

public class DoRiskAnalysisRequestBuilder {

    private AggregateId sagaDataId;

    private DateTime applicationTime;

    private Money loanValue;

    private DateTime expirationDate;

    public DoRiskAnalysisRequestBuilder(){

    }

    public DoRiskAnalysisRequestBuilder withRiskedData(){
        sagaDataId = AggregateId.generate();
        applicationTime = new DateTime().withTimeAtStartOfDay().plusHours(1);
        loanValue = new Money(3000);
        expirationDate = applicationTime.plusDays(30);

        return this;
    }

    public DoRiskAnalysisRequestBuilder withNoRiskedData(){
        sagaDataId = AggregateId.generate();
        applicationTime = new DateTime().withTimeAtStartOfDay().plusHours(9);
        loanValue = new Money(2000);
        expirationDate = applicationTime.plusDays(30);

        return this;
    }

    public DoRiskAnalysisRequest build(){
        return new DoRiskAnalysisRequest(sagaDataId, applicationTime, loanValue, expirationDate);
    }

}
