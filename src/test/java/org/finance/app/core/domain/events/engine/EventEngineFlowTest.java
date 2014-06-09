package org.finance.app.core.domain.events.engine;

import org.finance.app.core.domain.events.engine.mocks.BaseEventReceiveNotifier;
import org.finance.app.core.domain.events.engine.mocks.RandomEventReceiveNotifier;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.ddd.annotation.Event;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.test.ConfigTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class EventEngineFlowTest {

    private ApplicationContext applicationContext;

    private DomainEventPublisher eventPublisher;

    private static final String BaseEventHandlerName = "BaseEventReceiveNotifier" ;
    private static final String RandomEventHandlerName = "RandomEventReceiveNotifier" ;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setEventPublisher(DomainEventPublisher domainEventPublisher){
        this.eventPublisher = domainEventPublisher;
    }

    @Test
    public void publishedEventShouldBeReceivedByAppropriateHandler(){
        //Given
        Serializable wellKnowEvent = new WellKnownEvent();

        Class <?> wellKnownEventType = wellKnowEvent.getClass();

        BaseEventReceiveNotifier baseEventReceiveNotifier = null;

        //When
        try {
            Method method = BaseEventReceiveNotifier.class.getMethod("handle", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler(wellKnownEventType, BaseEventHandlerName, method, applicationContext);

            eventPublisher.registerEventHandler(eventHandler);
            eventPublisher.publish(wellKnowEvent);

            baseEventReceiveNotifier = (BaseEventReceiveNotifier) applicationContext.getBean(BaseEventHandlerName);

        //Then
        } catch(NoSuchMethodException ex) {
            fail();
        }
        assertTrue(baseEventReceiveNotifier.isRightEventOccurred());
        baseEventReceiveNotifier.cleanUpEventShadow();
    }


    @Test
    public void appearedRandomEventShouldNotBeHandle(){
        //Given
        Serializable randomEvent = new RandomEvent();
        Serializable wellKnowEvent = new WellKnownEvent();

        Class <?> randomEventType = randomEvent.getClass();
        Class <?> wellKnownEventType = wellKnowEvent.getClass();
        BaseEventReceiveNotifier baseEventReceiveNotifier = null;

        //When
        try {
            Method method = BaseEventReceiveNotifier.class.getMethod("handle", new Class[]{Object.class});

            SpringEventHandler eventHandler = new SpringEventHandler(wellKnownEventType, BaseEventHandlerName, method, applicationContext);
            eventPublisher.registerEventHandler(eventHandler);
            eventPublisher.publish(randomEvent);

            baseEventReceiveNotifier = (BaseEventReceiveNotifier) applicationContext.getBean(BaseEventHandlerName);

        } catch(NoSuchMethodException ex){
            fail();
        }

        //Then
        assertFalse(baseEventReceiveNotifier.isRightEventOccurred());
        baseEventReceiveNotifier.cleanUpEventShadow();
    }

    @Test
    public void twoDifferentEventsAppearsTwoHandlersMustCatchThem(){
        //Given
        Serializable randomEvent = new RandomEvent();
        Serializable wellKnowEvent = new WellKnownEvent();

        Class <?> randomEventType = randomEvent.getClass();
        Class <?> wellKnownEventType = wellKnowEvent.getClass();
        BaseEventReceiveNotifier baseEventReceiveNotifier = null;
        RandomEventReceiveNotifier randomEventReceiveNotifier = null;
        Boolean isOnlyRandomHandlerHandleCurrentEvent = false;
        Boolean isOnlyBaseHandlerHandleCurrentEvent = false;
        //When
        try {
            Method methodFromBaseHandler   = BaseEventReceiveNotifier.class.getMethod("handle", new Class[]{Object.class});
            Method methodFromRandomHandler = RandomEventReceiveNotifier.class.getMethod("handle", new Class[]{Object.class});

            SpringEventHandler baseEventHandler   = new SpringEventHandler(wellKnownEventType, BaseEventHandlerName, methodFromBaseHandler, applicationContext);
            SpringEventHandler randomEventHandler = new SpringEventHandler(randomEventType, RandomEventHandlerName, methodFromRandomHandler, applicationContext);
            eventPublisher.registerEventHandler(baseEventHandler);
            eventPublisher.registerEventHandler(randomEventHandler);

            eventPublisher.publish(randomEvent);

            Boolean isRandomHandlerHandleEvent = ( (RandomEventReceiveNotifier) applicationContext.getBean(RandomEventHandlerName) ).isRightEventOccurred();
            Boolean isBaseHandlerHandleEvent   = ( (BaseEventReceiveNotifier) applicationContext.getBean(BaseEventHandlerName) ).isRightEventOccurred();

            isOnlyRandomHandlerHandleCurrentEvent = (isRandomHandlerHandleEvent == true && isBaseHandlerHandleEvent == false);

            eventPublisher.publish(wellKnowEvent);

            isRandomHandlerHandleEvent = ( (RandomEventReceiveNotifier) applicationContext.getBean(RandomEventHandlerName) ).isRightEventOccurred();
            isBaseHandlerHandleEvent   = ( (BaseEventReceiveNotifier) applicationContext.getBean(BaseEventHandlerName) ).isRightEventOccurred();

            isOnlyBaseHandlerHandleCurrentEvent = (isRandomHandlerHandleEvent == false && isBaseHandlerHandleEvent == true);

        } catch(NoSuchMethodException ex){
            fail();
        }

        //Then
        assertTrue(isOnlyRandomHandlerHandleCurrentEvent);
        assertTrue(isOnlyBaseHandlerHandleCurrentEvent);

    }

    @Event
    class RandomEvent implements Serializable{
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
    class WellKnownEvent implements Serializable{
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

