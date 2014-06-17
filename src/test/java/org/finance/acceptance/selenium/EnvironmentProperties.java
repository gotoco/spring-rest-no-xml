package org.finance.acceptance.selenium;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * A VM argument providing the properties path parameter is needed.
 * for example: -DacceptanceEnvironment.properties=/Users/nestor/projects/config/acceptanceEnvironment.properties
 *
 * Here is an example of such a property file
 * <pre>* #acceptanceEnvironment.propeties
 * baseUrl=http://localhost:8080
 * webDriverClass=org.openqa.selenium.htmlunit.HtmlUnitDriver
 * adminUserName=admin@nestorurquiza.com
 * adminUserPassword=test
 * regularUserName=user@nestorurquiza.com
 * regularUserPassword=test
 * </pre>*
 */
public class EnvironmentProperties {
    private static final String ACCEPTANCE_ENVIRONMENT_PROPERTIES = "acceptanceEnvironment.properties";
    private static final EnvironmentProperties INSTANCE = new EnvironmentProperties();
    private static Properties properties = null;

    private EnvironmentProperties() {
    }

    public static Properties getInstance() throws FileNotFoundException {
        if(properties == null) {
            properties = new Properties();
            //Use -DacceptanceEnvironment.properties=/Users/nestor/projects/config/acceptanceEnvironment.properties
            String acceptanceEnvironmentPropertiesPath = System.getProperty(ACCEPTANCE_ENVIRONMENT_PROPERTIES);
            if(acceptanceEnvironmentPropertiesPath != null && acceptanceEnvironmentPropertiesPath.trim().length() > 0){
                InputStream in = new FileInputStream(acceptanceEnvironmentPropertiesPath);
                if(in != null) {
                    try {
                        properties.load(in);
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return properties;
    }

    public static WebDriver getWebDriver() throws FileNotFoundException {
        //The simplest not environment specific Driver
        WebDriver webDriver = new HtmlUnitDriver();
        Class<?> c;
        try {
            c = Class.forName(EnvironmentProperties.getInstance().getProperty("webDriverClass"));
            webDriver = (WebDriver) c.newInstance();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return webDriver;
    }

    public static String getBaseUrl() throws FileNotFoundException {
        return EnvironmentProperties.getInstance().getProperty("baseUrl");
    }

    public static String getAdminUserName() throws FileNotFoundException {
        return EnvironmentProperties.getInstance().getProperty("adminUserName");
    }

    public static String getAdminUserPassword() throws FileNotFoundException {
        return EnvironmentProperties.getInstance().getProperty("adminUserPassword");
    }

    public static String getRegularUserName() throws FileNotFoundException {
        return EnvironmentProperties.getInstance().getProperty("regularUserName");
    }

    public static String getRegularUserPassword() throws FileNotFoundException {
        return EnvironmentProperties.getInstance().getProperty("regularUserPassword");
    }
}