package org.finance.app.core.domain.businessprocess.loangrant.mocks;

import org.finance.app.core.domain.events.engine.mocks.AbstractEventReceiver;
import org.finance.app.core.domain.events.impl.saga.IpCheckedResponse;
import org.springframework.stereotype.Component;

/**
 * Created by maciek on 09.06.14.
 */
@Component("ipCheckedResponseHandler")
public class IpCheckedResponseHandler extends AbstractEventReceiver {

    private Boolean validIpAddress;

    public void handle(Object event) {
        super.handle(event);
        IpCheckedResponse response = (IpCheckedResponse)event;
        validIpAddress = response.getValidIpAddress();
    }

    public Boolean isValidIpAddress() {
        return validIpAddress;
    }
}
