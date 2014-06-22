package org.finance.app.spring.loaders;

import com.google.common.collect.ImmutableList;
import org.finance.app.core.domain.risk.Risk;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("permanentDataLoader")
public class PermanentDataLoader {

    private static final String basicRejectCase = "Application was submitted in risky night hours with max possible value";
    private static final String testRejectCase = "blablabalsa";

    private List<Risk> risks = ImmutableList.of(new Risk(basicRejectCase), new Risk(testRejectCase)).asList();

    private PermanentDataLoader dataLoader;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void loadData(){

        Query query = entityManager.createQuery("from Risk");
        final List<Risk> existingRisks = query.getResultList();

        for(Risk risk : risks){
            if(!existingRisks.contains(risk)){
                entityManager.persist(risk);
            }
        }

    }
}
