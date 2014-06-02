package org.finance.app.mocks.event;

/**
 * Created by maciek on 02.06.14.
 */

import junit.framework.Assert;

import org.finance.app.core.domain.common.Form;
import org.finance.app.core.domain.events.handlers.SpringEventHandler;
import org.finance.app.core.domain.events.impl.RequestWasSubmitted;
import org.finance.app.ddd.annotation.Event;
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
public class EventReceiveNotifierTest {

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Test
    public void shouldReceiveAppropriateEvents(){
        //Given
        Object randomEvent = new Serializable(){ int randomEventId; };

        Class <?> eventType = randomEvent.getClass();
        EventReceiveNotifier eventReceiveNotifier = null;

        //When
        try {
            Method method = EventReceiveNotifier.class.getMethod("handle", new Class[]{Object.class});

            new SpringEventHandler(eventType, "EventReceiveNotifier", method, applicationContext).handle(randomEvent);

            eventReceiveNotifier = (EventReceiveNotifier) applicationContext.getBean("EventReceiveNotifier");

        } catch(NoSuchMethodException ex){
            fail();
        }

        //Then
        assertTrue(eventReceiveNotifier.isRightEventOccurred(eventType));
    }

    @Test
    public void shouldNotReceiveDifferentEvents(){
        //Given
        Object randomEvent = new RandomEvent();
        Object wellKnowEvent = new WellKnownEvent();

        Class <?> randomEventType = randomEvent.getClass();
        Class <?> wellKnownEventType = wellKnowEvent.getClass();
        EventReceiveNotifier eventReceiveNotifier = null;

        //When
        try {
            Method method = EventReceiveNotifier.class.getMethod("handle", new Class[]{Object.class});

            new SpringEventHandler(randomEventType, "EventReceiveNotifier", method, applicationContext).handle(randomEventType);

            eventReceiveNotifier = (EventReceiveNotifier) applicationContext.getBean("EventReceiveNotifier");

        } catch(NoSuchMethodException ex){

            fail();
        }

        //Then
        assertFalse(eventReceiveNotifier.isRightEventOccurred(wellKnownEventType));
    }

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

