package org.finance.app.mocks.event;

/**
 * Created by maciek on 02.06.14.
 */

import junit.framework.Assert;
import org.finance.app.ddd.annotation.Event;
import org.junit.Test;

import java.io.Serializable;

public class EventReceiveNotifierTest {

    @Test
    public void shouldThrowExceptionAtSameEvents(){

    }

    @Test
    public void noExceptionOnDifferentEvents(){

        Object obj = "String";

        System.out.println("obj.getClass() : " + obj.getClass());
        System.out.println("string.getClass() : " + "String".getClass());

        Assert.assertEquals(obj.getClass(), "String".getClass());

    }

}

@Event
class RootEvent implements Serializable{

}

@Event
class FakeEvent implements Serializable{

}