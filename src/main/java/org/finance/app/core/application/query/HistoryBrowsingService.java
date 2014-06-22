package org.finance.app.core.application.query;

import org.finance.app.bports.crudes.ContractSchedulerPort;
import org.finance.app.core.ddd.annotation.ApplicationService;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.LoanContract;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("historyBrowsingService")
@ApplicationService
public class HistoryBrowsingService implements ContractSchedulerPort {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<LoanContract> getAllContractsOfUser(Client client) {

        String selectFromContractsWhere = "from LoanContract lc where lc.contractHolder = :client";

        Query selectSpecifiedLoan = entityManager.createQuery(selectFromContractsWhere)
                .setParameter("client", client);

        List <LoanContract> userContracts =  selectSpecifiedLoan.getResultList();

        return userContracts;

    }
}
