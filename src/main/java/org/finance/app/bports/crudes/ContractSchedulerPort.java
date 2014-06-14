package org.finance.app.bports.crudes;

import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.LoanContract;

import java.util.List;

public interface ContractSchedulerPort {

    public List<LoanContract> getAllContractsOfUser(Client client);

}
