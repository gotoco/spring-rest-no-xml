package org.finance.app.core.application.applicationservices;

import org.finance.app.adapters.webservices.json.FormJSON;
import org.finance.app.core.application.query.ClientFinder;
import org.finance.app.core.application.query.UserAuthorizationService;
import org.finance.app.core.application.translation.ApplicationFormTranslator;
import org.finance.app.core.domain.CustomerService;
import org.finance.app.ports.crudes.LoanReaderService;
import org.finance.app.sharedcore.objects.Client;
import org.finance.app.sharedcore.objects.Form;
import org.finance.app.sharedcore.objects.Loan;
import org.finance.app.core.ddd.annotation.ApplicationService;
import org.finance.app.ports.services.LoanServiceApi;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("CustomerServiceFacade")
@ApplicationService
public class CustomerServiceFacade implements LoanServiceApi {

    private CustomerService customerService;

    private LoanReaderService loanReaderService;

    private ApplicationFormTranslator applicationFormTranslator;

    private ClientFinder clientFinder;

    private UserAuthorizationService userAuthorizationService;

    @Autowired
    public void setClientFinder(ClientFinder clientFinder){
        this.clientFinder = clientFinder;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setLoanReaderService(LoanReaderService loanReaderService) {
        this.loanReaderService = loanReaderService;
    }

    @Autowired
    public void setUserAuthorizationService(UserAuthorizationService userAuthorizationService){
        this.userAuthorizationService = userAuthorizationService;
    }

    @Autowired
    public void setApplicationFormTranslator(ApplicationFormTranslator applicationFormTranslator){
        this.applicationFormTranslator = applicationFormTranslator;
    }

    public void applyForLoan(FormJSON jsonForm, DateTime submissionDate){
        Long clientId = userAuthorizationService.getOrCreateClient(jsonForm);
        Client customer = clientFinder.findClientById(clientId);
        Form domainForm = applicationFormTranslator.createFormFromRequest(jsonForm, customer, submissionDate);

        customerService.applyForaLoan(domainForm);
    }

    public void extendTheLoan(Long loanId, Long clientId){

        Loan loan = loanReaderService.getLoanByLoanIdAndUserId(loanId, clientId);

        customerService.extendTheLoan(loan);
    }

}
