package org.finance.app.spring;

import com.google.common.collect.ImmutableList;
import org.finance.app.core.domain.risk.Risk;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.List;

public class WebInit implements WebApplicationInitializer {

    private static final String basicRejectCase = "Application was submitted in risky night hours with max possible value";
    private static final String testRejectCase = "blablabalsa";

    private List<Risk> risks = ImmutableList.of(new Risk(basicRejectCase), new Risk(testRejectCase)).asList();

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        loadData();

    }


    public void loadData(){

    }

}
