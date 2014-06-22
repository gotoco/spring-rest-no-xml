package org.finance.app.core.domain.events.handlers;

import junit.framework.Assert;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.core.ddd.annotation.Event;
import org.finance.test.ConfigTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.Serializable;
import java.lang.reflect.Method;

import static junit.framework.Assert.fail;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class SpringEventHandlerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BasicEventConsumer basicEventConsumer;

    @Autowired
    private AopEventApi aopEventConsumer;

    private static final String basicEventConsumerName = "basicEventConsumer";
    private static final String aopEventConsumerName = "aopEventConsumer";

    @Test
    public void shouldCanHandleBasicEvent(){

        //Given
        SpringEventHandler eventHandler = prepareSpringEventHandlerForBasicEvent();

        //When
        Boolean canHandle = checkIfHandlerCanHandleEvent(eventHandler, new BasicEvent());

        //Then
        Assert.assertTrue(canHandle);
    }

    @Test
    public void shouldHandleBasicEvent(){
        //Given
        SpringEventHandler eventHandler = prepareSpringEventHandlerForBasicEvent();

        //When
        eventHandler.handle(new BasicEvent());

        //Then
        Assert.assertTrue(basicEventConsumer.isRightEventOccurred() );
    }

    @Test
    public void throwExceptionWhenUnknownEventHandle(){
        //Given
        SpringEventHandler eventHandler = prepareSpringEventHandlerForBasicEvent();

        //When
        Boolean tryToHandleEvent = tryToHandleEvent(eventHandler, new UnknownEvent());

        //Then
        Assert.assertFalse(tryToHandleEvent);
    }

    @Test
    public void shouldNotCanHandleUnknownEvent(){
        //Given
        SpringEventHandler eventHandler = prepareSpringEventHandlerForBasicEvent();

        //When
        Boolean checkIfCanHandleEvent = checkIfHandlerCanHandleEvent(eventHandler, new UnknownEvent());

        //Then
        Assert.assertFalse(checkIfCanHandleEvent );
    }

    @Test
    public void shouldCanHandleChildrenEvent(){
        //Given
        SpringEventHandler eventHandler = prepareSpringEventHandlerForBasicEvent();

        //When
        Boolean checkIfCanHandleEvent = checkIfHandlerCanHandleEvent(eventHandler, new ChildrenOfBasicEvent());

        //Then
        Assert.assertTrue(checkIfCanHandleEvent );
    }

    @Test
    public void shouldNotCanHandleParentEvent(){
        //Given
        SpringEventHandler eventHandler = prepareSpringEventHandlerForChildrenOfBasicEvent();

        //When
        Boolean checkIfCanHandleEvent = checkIfHandlerCanHandleEvent(eventHandler, new BasicEvent());

        //Then
        Assert.assertFalse(checkIfCanHandleEvent );
    }

    @Test
    public void shouldCanHandleTransactionalMethod(){

        SpringEventHandler aopHandler = prepareSpringEventHandlerForAopBean();

        Boolean canHandle = aopHandler.canHandle(new BasicEvent());

        Assert.assertTrue(canHandle);
    }

    @Test
    public void shouldHandleTransactionalMethod(){

        SpringEventHandler aopHandler = prepareSpringEventHandlerForAopBean();

        aopHandler.handle(new BasicEvent());

        Assert.assertTrue(aopEventConsumer.ifBasicEventOccurred());
    }

    @Test
    public void shouldCanHandleFromProxyInterface(){

        SpringEventHandler aopHandler = prepareSpringEventHandlerForAopProxy();

        Boolean canHandle = aopHandler.canHandle(new BasicEvent());

        Assert.assertTrue(canHandle);
    }

    @Test
    public void shouldHandleFromProxyInterface(){

        SpringEventHandler aopHandler = prepareSpringEventHandlerForAopProxy();

        aopHandler.handle(new BasicEvent());

        Assert.assertTrue(aopEventConsumer.ifBasicEventOccurred());
    }


    private SpringEventHandler prepareSpringEventHandlerForBasicEvent() {
        SpringEventHandler eventHandler = null;
        Serializable basicEvent = new BasicEvent();
        Class<?> basicEventType = basicEvent.getClass();

        try {
            Method method = BasicEventConsumer.class.getMethod("handle", new Class[]{Object.class});

            eventHandler = new SpringEventHandler(basicEventType, basicEventConsumerName, method, applicationContext);

        } catch (NoSuchMethodException ex) {
            fail();
        }
        return eventHandler;
    }

    private SpringEventHandler prepareSpringEventHandlerForChildrenOfBasicEvent() {
        SpringEventHandler eventHandler = null;
        Serializable childrenEvent = new ChildrenOfBasicEvent();
        Class<?> childrenEventType = childrenEvent.getClass();

        try {
            Method method = BasicEventConsumer.class.getMethod("handle", new Class[]{Object.class});

            eventHandler = new SpringEventHandler(childrenEventType, basicEventConsumerName, method, applicationContext);

        } catch (NoSuchMethodException ex) {
            fail();
        }
        return eventHandler;
    }

    private SpringEventHandler prepareSpringEventHandlerForAopBean() {
        SpringEventHandler eventHandler = null;
        Serializable basicEvent = new BasicEvent();
        Class<?> basicEventType = basicEvent.getClass();

        try {
            Method method = AopEventConsumer.class.getMethod("transactionalMethod", new Class[]{Object.class});

            eventHandler = new SpringEventHandler(basicEventType, aopEventConsumerName, method, applicationContext);

        } catch (NoSuchMethodException ex) {
            fail();
        }
        return eventHandler;
    }

    private SpringEventHandler prepareSpringEventHandlerForAopProxy() {
        SpringEventHandler eventHandler = null;
        Serializable basicEvent = new BasicEvent();
        Class<?> basicEventType = basicEvent.getClass();

        try {
            Method method = AopEventApi.class.getMethod("transactionalMethod", new Class[]{Object.class});

            eventHandler = new SpringEventHandler(basicEventType, aopEventConsumerName, method, applicationContext);

        } catch (NoSuchMethodException ex) {
            fail();
        }
        return eventHandler;
    }


    private Boolean tryToHandleEvent(SpringEventHandler eventHandler, Serializable event){
        Boolean result;
        try {
            eventHandler.handle(event);
            result = true;
        } catch(Exception ex){
            result = false;
        }
        return result;
    }

    private Boolean checkIfHandlerCanHandleEvent(SpringEventHandler eventHandler, Serializable event){
        return eventHandler.canHandle(event);
    }

    @Event
    class BasicEvent implements Serializable {
        String thisName = "basicEvent";

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BasicEvent)) return false;

            BasicEvent that = (BasicEvent) o;

            if (thisName != null ? !thisName.equals(that.thisName) : that.thisName != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return thisName != null ? thisName.hashCode() : 0;
        }
    }

    @Event
    class UnknownEvent implements Serializable {
        String thisName = "unknownEvent";

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BasicEvent)) return false;

            BasicEvent that = (BasicEvent) o;

            if (thisName != null ? !thisName.equals(that.thisName) : that.thisName != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return thisName != null ? thisName.hashCode() : 0;
        }
    }

    @Event
    class ChildrenOfBasicEvent extends BasicEvent implements Serializable {
        String thisName = "childrenOfBasicEvent";

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BasicEvent)) return false;

            BasicEvent that = (BasicEvent) o;

            if (thisName != null ? !thisName.equals(that.thisName) : that.thisName != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return thisName != null ? thisName.hashCode() : 0;
        }
    }
}
