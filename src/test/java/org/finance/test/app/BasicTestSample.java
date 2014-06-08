package org.finance.test.app;

import junit.framework.Assert;
import org.finance.test.ConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes = {ConfigTest.class})
public class BasicTestSample {

    @Test
    public void testIfSpringTestsWorks(){

        Assert.assertEquals(true, true);
    }
}