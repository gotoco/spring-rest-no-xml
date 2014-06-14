package org.finance.app.spring;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.google.common.collect.ImmutableList;
import org.finance.app.core.domain.risk.Risk;
import org.finance.app.spring.loaders.PermanentDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.WebApplicationInitializer;

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
