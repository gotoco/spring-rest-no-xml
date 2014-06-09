package org.finance.app.core.domain.events.impl.saga;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.ddd.annotation.Event;

import java.io.Serializable;

/**
 * Created by maciek on 08.06.14.
 */
@Event
public class RiskAnalyzedResponse implements Serializable {

    private final AggregateId sagaDataId;

    public RiskAnalyzedResponse(AggregateId sagaDataId){
        this.sagaDataId = sagaDataId;
    }

    public AggregateId getSagaDataId() {
        return sagaDataId;
    }
}
