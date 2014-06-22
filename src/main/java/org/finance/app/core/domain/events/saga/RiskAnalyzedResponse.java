package org.finance.app.core.domain.events.saga;

import org.finance.app.core.ddd.annotation.Event;
import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.domain.risk.Risk;

import java.io.Serializable;
import java.util.List;

@Event
public class RiskAnalyzedResponse implements Serializable {

    private final AggregateId sagaDataId;

    private final List<Risk> risks;

    public RiskAnalyzedResponse(AggregateId sagaDataId, List<Risk> risk){
        this.sagaDataId = sagaDataId;
        this.risks = risk;
    }

    public AggregateId getSagaDataId() {
        return sagaDataId;
    }

    public List<Risk> getRisks() {
        return risks;
    }

    public Boolean hasRisk(){
        return !risks.isEmpty();
    }
}
