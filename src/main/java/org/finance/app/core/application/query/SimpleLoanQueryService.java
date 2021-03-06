package org.finance.app.core.application.query;


import org.finance.app.core.ddd.annotation.ApplicationService;
import org.finance.app.bports.crudes.LoanReaderService;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Loan;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component("simpleLoanQueryService")
@ApplicationService
public class SimpleLoanQueryService implements LoanReaderService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Loan getLoanByLoanIdAndUserId(Long loanId, Client client) {
        String queryBody = "from Loan l where l.loanId = :requestId AND l.loanHolder = :userId";

        Query selectSpecifiedLoan = entityManager.createQuery(queryBody)
                .setParameter("requestId", loanId)
                .setParameter("userId", client);

        Loan entityToUpdate = (Loan) selectSpecifiedLoan.getSingleResult();

        return entityToUpdate;
    }
}
