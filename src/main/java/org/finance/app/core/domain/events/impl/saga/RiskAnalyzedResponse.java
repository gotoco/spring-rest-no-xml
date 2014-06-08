package org.finance.app.core.domain.events.impl.saga;

import org.finance.app.ddd.annotation.Event;

import java.io.Serializable;

/**
 * Created by maciek on 08.06.14.
 */
@Event
public class RiskAnalyzedResponse implements Serializable {

    private final Long sagaDataId;

    public RiskAnalyzedResponse(Long sagaDataId){
        this.sagaDataId = sagaDataId;
    }

}
