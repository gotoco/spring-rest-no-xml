package org.finance.app.core.application.applicationservices;

import junit.framework.Assert;
import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.annotations.IntegrationTest;
import org.finance.app.bports.crudes.ContractSchedulerPort;
import org.finance.app.bports.services.LoanService;
import org.finance.app.core.application.parent.ApplicationEnvSpecifiedFunctionalities;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.sharedcore.objects.LoanContract;
import org.finance.test.ConfigTest;
import org.finance.test.builders.contracts.LoanContractBuilder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = ConfigTest.class)
public class CustomerServiceFacadeTest extends ApplicationEnvSpecifiedFunctionalities {

    @Autowired
    private LoanService customerServiceFacade;

    @Autowired
    private ContractSchedulerPort contractScheduler;

    @Test
    @Transactional
    @Rollback(true)
    public void whenCorrectSubmissionNewLoanIsCreated(){

        //Given
        Client newClient = createSaveAndGetNewClient();
        FormJSON form = getApplicationFormWithCorrectUser(newClient);

        //When
        customerServiceFacade.applyForLoan(form, new DateTime());

        //Then
        Assert.assertTrue(checkIfExistLoanForClient(newClient));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void whenRequestedForExtendLoanNewLoanPeriodIsCreated(){

        //Given
        LoanContract baseContract = new LoanContractBuilder().withDefaultData().build();
        Loan baseLoan = baseContract.getLatestGrantedLoan();
        entityManager.persist(baseContract);

        //When
        customerServiceFacade.extendTheLoan(baseLoan.getLoanId(),
                                            baseLoan.getLoanHolder().getClientId(),
                                            new DateTime());
        Boolean allMatch = checkIfAllLoansFromAllContractAreExtend(baseLoan);

        //Then
        Assert.assertTrue(allMatch);
    }

    public Boolean checkIfAllLoansFromAllContractAreExtend(Loan baseLoan){

        List<LoanContract> allContracts = contractScheduler.getAllContractsOfUser(baseLoan.getLoanHolder());
        List<LoanContract> extendedContracts = allContracts.stream()
                .filter(loanContract -> !loanContract.getLoanPeriods()
                        .stream()
                        .filter(loan -> baseLoan.getLoanId()
                                                .equals(loan.getBasedOnLoanId()))
                        .collect(Collectors.toList()).isEmpty())
                .collect(Collectors.toList());

        return extendedContracts.stream().allMatch(contract -> contract.getLatestGrantedLoan().getBasedOnLoanId().equals(baseLoan.getLoanId()));
    }

    public FormJSON getApplicationFormWithCorrectUser(Client newClient){
        FormJSON form = createFormForUser(newClient);

        return form;
    }

    @Transactional
    public Boolean checkIfExistLoanForClient(Client client){
        String selectLoanWhereUser = "from Loan l where l.loanHolder = :client";

        Query selectSpecifiedLoan = entityManager.createQuery(selectLoanWhereUser)
                .setParameter("client", client);

        return !selectSpecifiedLoan.getResultList().isEmpty();
    }
}