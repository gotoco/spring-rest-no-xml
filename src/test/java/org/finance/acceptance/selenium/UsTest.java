package org.finance.acceptance.selenium;

import java.net.URI;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public abstract class UsTest {

    @Autowired
    private URI siteBase;

    @Autowired
    private WebDriver drv;

    public URI getSiteBase() {
        return siteBase;
    }

    public WebDriver getDrv() {
        return drv;
    }

    @Before
    public void setUp() {
        getDrv().manage().deleteAllCookies();
        getDrv().get(siteBase.toString());
    }
}
