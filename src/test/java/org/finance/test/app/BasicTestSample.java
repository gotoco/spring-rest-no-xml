package org.finance.test.app;

import junit.framework.Assert;
import org.finance.test.ConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = {ConfigTest.class})
public class BasicTestSample {

    @Test
    public void testIfSpringTestsWorks(){

        Assert.assertEquals(true, true);
    }
}