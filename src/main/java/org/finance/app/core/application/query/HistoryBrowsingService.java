package org.finance.app.core.application.query;

import org.finance.app.core.ddd.annotation.ApplicationService;
import org.finance.app.bports.crudes.ContractSchedulerPort;
import org.finance.app.sharedcore.objects.LoanContract;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("HistoryBrowsingService")
@ApplicationService
public class HistoryBrowsingService implements ContractSchedulerPort {


    @Override
    public List<LoanContract> getAllContractsOfUser(Long clientId) {
        return null;
    }
}
