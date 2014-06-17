package org.finance.acceptance.selenium;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Verifying behavior of UserStory1.
 */
public class UserStory1Test {

    private WebDriver driver = null;

    @Before
    public void aTearUpMethodForEachTest() throws FileNotFoundException {
        driver = EnvironmentProperties.getWebDriver();
    }

    @After
    public void aTearDownMethodForEachTest() {
        driver.close();
    }

    @Test
    public void givenNotLoggedInWhenAnyPageIsHitThenShouldBeAskedToAuthenticate() throws FileNotFoundException {

        String url = EnvironmentProperties.getBaseUrl() + "/client/list";
        driver.get(url);
        assertThat(driver.findElement(By.name("loginForm")).getAttribute("action"), is("j_spring_security_check"));
    }

    @Test
    public void givenLoggedInAndAdminUserRoleWhenAnAdminPageIsHitThenShouldSeeThePage() throws FileNotFoundException {
        String url = EnvironmentProperties.getBaseUrl() + "/";
        driver.get(url);
        WebElement loginForm = driver.findElement(By.name("loginForm"));
        WebElement userName = loginForm.findElement(By.name("j_username"));
        userName.sendKeys(EnvironmentProperties.getAdminUserName());
        WebElement password = loginForm.findElement(By.name("j_password"));
        password.sendKeys(EnvironmentProperties.getAdminUserPassword());
        loginForm.submit();
        url = EnvironmentProperties.getBaseUrl() + "/client/list";
        driver.get(url);

        //Not supported by HtmlUnit
        //assertThat(driver.findElement(By.cssSelector(".form-navigation")), is(notNullValue()));

        assertThat(driver.findElement(By.className("form-navigation")), is(notNullValue()));
    }

    @Test
    public void givenLoggedInAndRegularUserRoleWhenAnAdminPageIsHitThenShouldSeeAnErrorPage() throws FileNotFoundException {
        String url = EnvironmentProperties.getBaseUrl() + "/";
        driver.get(url);
        WebElement loginForm = driver.findElement(By.name("loginForm"));
        WebElement userName = loginForm.findElement(By.name("j_username"));
        userName.sendKeys(EnvironmentProperties.getRegularUserName());
        WebElement password = loginForm.findElement(By.name("j_password"));
        password.sendKeys(EnvironmentProperties.getRegularUserPassword());
        loginForm.submit();
        url = EnvironmentProperties.getBaseUrl() + "/client/list";
        driver.get(url);

        //Not supported by HtmlUnit
        //assertThat(driver.findElement(By.cssSelector(".form-navigation")), is(nullValue()));

        assertThat(driver.findElement(By.className("errors")), is(notNullValue()));
    }
}
