package org.finance.test;

/**
 * Created by maciek on 02.06.14.
 */

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.web.WebAppConfiguration;


@Configuration
@WebAppConfiguration
@ComponentScan({ "org.finance.app" })
public class ConfigTest {

    public ConfigTest() {
        super();
    }

}

