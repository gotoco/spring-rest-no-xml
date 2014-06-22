package org.finance.app.core.domain.businessprocess.loangrant.mocks;

import org.finance.app.core.domain.events.engine.mocks.AbstractEventReceiver;
import org.springframework.stereotype.Component;

@Component("checkIpRequestHandler")
public class CheckIpRequestHandler extends AbstractEventReceiver {

    public void handle(Object event) {
        super.handle(event);
    }

    public Boolean isRightEventOccurred(){
        return super.isRightEventOccurred();
    }

}
