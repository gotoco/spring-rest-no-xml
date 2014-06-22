package org.finance.app.core.domain.events.engine.mocks;

import org.finance.app.core.ddd.annotation.Event;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.junit.Assert.fail;

public abstract class EventBaseEnvironmentFunctionalities {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DomainEventPublisher eventPublisher;

    public Object createRegisterAndGetEventNotifier(Class<?> notifierClass, String notifierBeanName, Class<?> eventClass) {

        String notifiersBaseHandlingMethodName = "handle";

        try {
            Method notifierMethod = notifierClass.getMethod(notifiersBaseHandlingMethodName, new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler
                    (eventClass, notifierBeanName, notifierMethod, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);

            return applicationContext.getBean(notifierBeanName);

        } catch(NoSuchMethodException ex) {
            fail(ex.getMessage());
        }
        return null;
    }

    @Event
    public class RandomEvent implements Serializable {
        int randomId=0;

        public int getRandomId() {
            return randomId;
        }

        public void setRandomId(int randomId) {
            this.randomId = randomId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RandomEvent)) return false;

            RandomEvent that = (RandomEvent) o;

            if (randomId != that.randomId) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return randomId;
        }
    }

    @Event
    public class WellKnownEvent implements Serializable{
        String wellKnownDisplayName="wellKnownDisplayName";

        public String getWellKnownDisplayName() {
            return wellKnownDisplayName;
        }

        public void setWellKnownDisplayName(String wellKnownDisplayName) {
            this.wellKnownDisplayName = wellKnownDisplayName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WellKnownEvent)) return false;

            WellKnownEvent that = (WellKnownEvent) o;

            if (wellKnownDisplayName != null ? !wellKnownDisplayName.equals(that.wellKnownDisplayName) : that.wellKnownDisplayName != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return wellKnownDisplayName != null ? wellKnownDisplayName.hashCode() : 0;
        }
    }
}
