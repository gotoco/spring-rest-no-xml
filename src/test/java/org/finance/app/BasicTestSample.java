package org.finance.app;

/**
 * Created by maciek on 02.06.14.
 */

import junit.framework.Assert;
import org.finance.test.ConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class,
        loader = AnnotationConfigWebContextLoader.class)
public class BasicTestSample {


    @Test
    public void testIfTestsWorks(){

        System.out.println("#### Tests Run:)");

        Assert.assertEquals(true, true);
    }
}