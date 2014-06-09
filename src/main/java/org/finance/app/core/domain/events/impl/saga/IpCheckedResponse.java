package org.finance.app.core.domain.events.impl.saga;

import org.finance.app.core.domain.common.AggregateId;
import org.finance.app.core.ddd.annotation.Event;

import java.io.Serializable;

/**
 * Created by maciek on 08.06.14.
 */
@Event
public class IpCheckedResponse implements Serializable {

    private final AggregateId sagaDataId;
    private final Boolean validIpAddress;

    public IpCheckedResponse(AggregateId sagaDataId, Boolean isValidIpAddress){
        this.sagaDataId = sagaDataId;
        this.validIpAddress = isValidIpAddress;
    }

    public AggregateId getSagaDataId() {
        return sagaDataId;
    }

    public Boolean getValidIpAddress() {
        return validIpAddress;
    }
}
