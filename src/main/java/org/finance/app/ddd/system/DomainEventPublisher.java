package org.finance.app.ddd.system;

/**
 * Created by maciek on 02.06.14.
 */

import java.io.Serializable;

public interface DomainEventPublisher {
    void publish(Serializable event);
}
