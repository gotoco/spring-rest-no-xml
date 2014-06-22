package org.finance.app.core.domain.events.engine;

import org.finance.app.core.domain.events.engine.mocks.BaseEventReceiveNotifier;
import org.finance.app.core.domain.events.engine.mocks.EventBaseEnvironmentFunctionalities;
import org.finance.app.core.domain.events.engine.mocks.RandomEventReceiveNotifier;
import org.finance.app.core.ddd.system.DomainEventPublisher;
import org.finance.test.ConfigTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class EventEngineFlowTest extends EventBaseEnvironmentFunctionalities{

    @Autowired
    private DomainEventPublisher eventPublisher;

    private static final String BaseEventHandlerName = "BaseEventReceiveNotifier" ;
    private static final String RandomEventHandlerName = "RandomEventReceiveNotifier" ;

    @Test
    public void publishedEventShouldBeReceivedByAppropriateHandler(){
        //Given
        BaseEventReceiveNotifier baseEventReceiveNotifier = (BaseEventReceiveNotifier)createRegisterAndGetEventNotifier
                                                            (BaseEventReceiveNotifier.class, BaseEventHandlerName, WellKnownEvent.class);

        //When
        eventPublisher.publish(new WellKnownEvent());

        //Then
        assertTrue(baseEventReceiveNotifier.isRightEventOccurred());
    }

    @Test
    public void appearedRandomEventShouldNotBeHandle(){
        //Given
        BaseEventReceiveNotifier baseEventReceiveNotifier  = (BaseEventReceiveNotifier)createRegisterAndGetEventNotifier
                                                             (BaseEventReceiveNotifier.class, BaseEventHandlerName, WellKnownEvent.class);
        //When
        eventPublisher.publish(new RandomEvent());

        //Then
        assertFalse(baseEventReceiveNotifier.isRightEventOccurred());
    }

    @Test
    public void twoDifferentEventsAppearsTwoHandlersMustCatchThem(){

        //Given
        Boolean isOnlyRandomHandlerHandleCurrentEvent;
        Boolean isOnlyBaseHandlerHandleCurrentEvent;
        RandomEventReceiveNotifier randomEventHandler = (RandomEventReceiveNotifier)createRegisterAndGetEventNotifier
                (RandomEventReceiveNotifier.class, RandomEventHandlerName, RandomEvent.class);
        BaseEventReceiveNotifier baseEventHandler = (BaseEventReceiveNotifier)createRegisterAndGetEventNotifier
                (BaseEventReceiveNotifier.class, BaseEventHandlerName, WellKnownEvent.class);

        //When first event occurred
        eventPublisher.publish(new RandomEvent());
        Boolean isRandomHandlerHandleEvent = randomEventHandler.isRightEventOccurred();
        Boolean isBaseHandlerHandleEvent   = baseEventHandler.isRightEventOccurred();
        isOnlyRandomHandlerHandleCurrentEvent = (isRandomHandlerHandleEvent == true && isBaseHandlerHandleEvent == false);
        //When second event occurred
        eventPublisher.publish(new WellKnownEvent());
        isRandomHandlerHandleEvent = randomEventHandler.isRightEventOccurred();
        isBaseHandlerHandleEvent   = baseEventHandler.isRightEventOccurred();
        isOnlyBaseHandlerHandleCurrentEvent = (isRandomHandlerHandleEvent == false && isBaseHandlerHandleEvent == true);

        //Then After all
        assertTrue(isOnlyRandomHandlerHandleCurrentEvent);
        assertTrue(isOnlyBaseHandlerHandleCurrentEvent);

    }

}

