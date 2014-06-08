package org.finance.app.core.domain.events.impl.saga;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.ddd.annotation.Event;

import java.io.Serializable;

/**
 * Created by maciek on 08.06.14.
 */
@Event
public class CheckIpRequest implements Serializable {

    private final AggregateId sagaDataId;

    private final String addressIp;

    public CheckIpRequest(AggregateId sagaDataId, String addressIp){
        this.sagaDataId = sagaDataId;
        this.addressIp = addressIp;
    }
}
