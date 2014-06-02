package org.finance.app;

/**
 * Created by maciek on 02.06.14.
 */

import junit.framework.Assert;
import org.finance.app.core.domain.events.SimpleEventPublisher;
import org.finance.app.core.domain.events.impl.BasicEvent;
import org.finance.test.ConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.Serializable;
import java.lang.reflect.Method;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class,
        loader = AnnotationConfigWebContextLoader.class)
public class BasicTestSample {

    @Autowired
    SimpleEventPublisher publisher;

    @Autowired
    private BeanFactory beanFactory;

    @Test
    public void testIfTestsWorks(){

        System.out.println("#### Test Runed:)");

        SimpleEventPublisher bean = (SimpleEventPublisher) beanFactory.getBean("SimpleEventPublisher");
        Method method = null;

        try {
            method = bean.getClass().getMethod("publish", new Class[]{Serializable.class} );

            method.invoke(bean, new BasicEvent());
        }
catch(Exception ex){
    System.out.println(ex.getMessage());
}





        Assert.assertEquals(true, true);
    }
}