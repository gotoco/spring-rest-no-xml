package org.finance.app.spring;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.finance.app.core.domain.risk.Risk;
import org.finance.app.spring.loaders.PermanentDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.WebApplicationInitializer;

import java.util.List;

public class WebInit implements WebApplicationInitializer {

    private PermanentDataLoader dataLoader;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public WebInit(PermanentDataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {



        dataLoader.loadData();

    }



}
